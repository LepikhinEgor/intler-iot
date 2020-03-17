package intler_iot.services;

import intler_iot.controllers.entities.SensorPageDTO;
import intler_iot.controllers.entities.DeviceStateDTO;
import intler_iot.dao.SensorDao;
import intler_iot.dao.entities.Device;
import intler_iot.dao.entities.Sensor;
import intler_iot.dao.entities.User;
import intler_iot.services.converters.dto.SensorPageDTOConverter;
import intler_iot.services.exceptions.NotAuthException;
import intler_iot.services.exceptions.SensorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.*;

@Service
@EnableScheduling
public class SensorService {

    static final int SENSORS_ON_PAGE = 10;
    static final int LAST_PAGE_NUM = -1;

    private SensorPageDTOConverter sensorPageDTOConverter;

    private DeviceService deviceService;
    private UserService userService;

    private SensorDao sensorDao;

    private static final long DAY_IN_MILLIS = 1000*60*60*24;

    @Autowired
    public void setDeviceService(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setSensorDao(SensorDao sensorDao) {
        this.sensorDao = sensorDao;
    }

    @Autowired
    public void setSensorPageDTOConverter(SensorPageDTOConverter sensorPageDTOConverter) {
        this.sensorPageDTOConverter = sensorPageDTOConverter;
    }

    public void updateSensorsValues(DeviceStateDTO deviceStateDTO) throws NotAuthException {
        User user = userService.authUser(deviceStateDTO.getLogin(), deviceStateDTO.getPassword());
        deviceService.connectDevice(deviceStateDTO.getLogin(), deviceStateDTO.getPassword(), deviceStateDTO.getDeviceName(), deviceStateDTO.getDeviceType());
        Device device = deviceService.getDeviceById(user, deviceStateDTO.getDeviceName());

        recordSensorsValues(device, deviceStateDTO.getSensorsValue());
    }

    private void recordSensorsValues(Device device, HashMap<String, Double> sensorsValues) {
        List<Sensor> sensors = new ArrayList<Sensor>();

        for (String sensorName: sensorsValues.keySet()) {
            Sensor sensor = new Sensor();
            sensor.setName(sensorName);
            sensor.setValue(sensorsValues.get(sensorName));
            sensor.setDevice(device);

            sensors.add(sensor);
        }

        sensorDao.recordAll(sensors);
    }

    public HashMap<String, Collection<String>> getSensorsName() {
        User user = userService.getCurrentUser();
        List<Device> userDevices = deviceService.getUserDevices(user);

        HashMap<String, Collection<String>> devicesSensors = new HashMap<>();

        for (Device device : userDevices) {
            Collection<String> unicSensorsName = getUnicSensorNames(device.getSensors());
            devicesSensors.put(device.getName(), unicSensorsName);
        }

        return devicesSensors;
    }

    public void removeOldSensorsValue() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis() - DAY_IN_MILLIS);
        sensorDao.removeOldValues(timestamp);
    }

    public List<Sensor> getLastSensors(User user) {
        List<Sensor> lastSensors = sensorDao.getLastSensors(user);

        return lastSensors;
    }

    public List<SensorPageDTO> getUserSensors() throws NotAuthException {
        User user = userService.getCurrentUser();
        List<Device> userDevices = deviceService.getUserDevices(user);

        List<Sensor> sensors = sensorDao.getAll(userDevices);
        List<SensorPageDTO> sensorsPage = transformToSensorsPage(sensors,userDevices);

        return sensorsPage;
    }

    /**
     * Method for getting sorted sensors value as table page. If needed sensor has not values throwing exception. This case may be when
     * user request table page, but this sensor values was deleted recently
     * @param sensorName
     * @param pageNum
     * @return table page with sensor values
     * @throws NotAuthException
     * @throws SensorNotFoundException
     */
    public SensorPageDTO getSensorLogPage(String sensorName, int pageNum) throws NotAuthException, SensorNotFoundException {
        User user = userService.getCurrentUser();
        List<Sensor> sensors = sensorDao.getSensorValues(sensorName, user);

        if (sensors.size() == 0)
            throw new SensorNotFoundException("Requested sensor page is empty, because not found sensor values from db. Propably " +
                    "sensor data was auto deleted and not updated");

        int pagesCount = calulatePagesCount(sensors.size());
        if (pageNum == LAST_PAGE_NUM || pageNum > pagesCount)
            pageNum = pagesCount -1;

        List<Sensor> sensorsPage = pullSensorPage(sensors, pageNum);

        SensorPageDTO sensorPageDTO = sensorPageDTOConverter.covertToDTO(sensorName, pageNum, pagesCount, sensorsPage);

        return sensorPageDTO;
    }

    public List<Sensor> pullSensorPage(List<Sensor> sensors, int pageNum) {
        sensors = sortSensors(sensors);

        List<Sensor> sensorsPage = new LinkedList<>();

        int firstSensor = pageNum * SENSORS_ON_PAGE;
        int lastSensor = firstSensor + SENSORS_ON_PAGE;

        for (int i = firstSensor; i < sensors.size() && i < lastSensor;i++) {
                sensorsPage.add(sensors.get(i));
        }

        return sensorsPage;
    }

    private List<Sensor> sortSensors(List<Sensor> sensors) {
        sensors.sort((Sensor s1, Sensor s2) -> s2.getArriveTime().compareTo(s1.getArriveTime()));

        return sensors;
    }

    private List<SensorPageDTO> transformToSensorsPage(List<Sensor> sensors, List<Device> devices) {
        Collection<String> unicSensors = getUnicSensorNames(sensors);
        List<SensorPageDTO> sensorPageDTOs = new ArrayList<>();

        sensors = sortSensors(sensors);

        for (String name : unicSensors) {
            List<Sensor> sensorValues = new LinkedList<>();

            int sensorsFounded = 0;
            for (Sensor sensor : sensors) {
                if (sensor.getName().equals(name)) {
                    if (sensorValues.size() < SENSORS_ON_PAGE) {
                        sensorValues.add(sensor);
                    }
                    sensorsFounded++;
                }
            }

            int pagesCount =  calulatePagesCount(sensorsFounded);
            SensorPageDTO sensorPageDTO = sensorPageDTOConverter.covertToDTO(name, 0,pagesCount, sensorValues);
            sensorPageDTOs.add(sensorPageDTO);
        }

        return sensorPageDTOs;
    }


    private Collection<String> getUnicSensorNames(List<Sensor> sensors) {
        HashSet<String> unicNames = new HashSet<>();

        for (Sensor sensor: sensors)
            unicNames.add(sensor.getName());

        return unicNames;
    }

    private int calulatePagesCount(int sensorsCount) {
        int pagesCount;
        if (sensorsCount % 10 == 0)
            pagesCount = sensorsCount / SENSORS_ON_PAGE;
        else
            pagesCount = sensorsCount / SENSORS_ON_PAGE + 1;

        return pagesCount;
    }
}
