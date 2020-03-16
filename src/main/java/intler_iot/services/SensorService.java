package intler_iot.services;

import intler_iot.controllers.entities.SensorLogDTO;
import intler_iot.controllers.entities.DeviceStateDTO;
import intler_iot.dao.SensorDao;
import intler_iot.dao.entities.Device;
import intler_iot.dao.entities.Sensor;
import intler_iot.dao.entities.User;
import intler_iot.services.exceptions.NotAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.*;

@Service
@EnableScheduling
public class SensorService {

    private static final int SENSORS_ON_PAGE = 10;

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
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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

    public List<SensorLogDTO> getUserSensors() throws NotAuthException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Device> userDevices = deviceService.getUserDevices(user);

        List<Sensor> sensors = sensorDao.getAll(userDevices);
        List<SensorLogDTO> lastUnicSensors = transformToSensorLogs(sensors,userDevices);

        return lastUnicSensors;
    }

    public SensorLogDTO getSensorLogPage(String sensorName, int pageNum) throws NotAuthException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Sensor> sensors = sensorDao.getSensorValues(sensorName, user);
        if (pageNum == -1)
            pageNum = sensors.size() / SENSORS_ON_PAGE;
        else
            pageNum = pageNum;
        int pagesCount = calulatePagesCount(sensors.size());
        List<Sensor> sensorsPage = pullSensorPage(sensors, pageNum);

        SensorLogDTO sensorLogDTO = new SensorLogDTO(sensorName, pageNum, pagesCount, sensorsToPairs(sensorsPage));

        return sensorLogDTO;
    }

    public List<Sensor> pullSensorPage(List<Sensor> sensors, int pageNum) {
        sensors.sort((Sensor s1, Sensor s2) -> s2.getArriveTime().compareTo(s1.getArriveTime()));
        List<Sensor> sensorsPage = new LinkedList<>();

        int startPage = pageNum * SENSORS_ON_PAGE;
        int endPage = startPage + SENSORS_ON_PAGE;

        for (int i = startPage; i < sensors.size() && i < endPage;i++) {
                sensorsPage.add(sensors.get(i));
        }

        return sensorsPage;
    }

    private List<SensorLogDTO> transformToSensorLogs(List<Sensor> sensors, List<Device> devices) {
        HashSet<String> unicSensors = new HashSet<String>();
        List<SensorLogDTO> sensorLogDTOS = new ArrayList<>();

        for(Sensor sensor : sensors) {
            unicSensors.add(sensor.getName());
        }

        sensors.sort((Sensor s1, Sensor s2) -> s2.getArriveTime().compareTo(s1.getArriveTime()));

        for (String name : unicSensors) {
            SensorLogDTO sensorLogDTO = new SensorLogDTO();
            sensorLogDTO.setSensorName(name);

            int sensorsFounded = 0;
            for (Sensor sensor : sensors) {
                if (sensor.getName().equals(name)) {
                    if (sensorsFounded < 10) {
                        Map.Entry<Timestamp, Double> sensorVal = new AbstractMap.SimpleEntry<Timestamp, Double>(sensor.getArriveTime(), sensor.getValue());
                        sensorLogDTO.getSensorsLogs().add(sensorVal);
                    }
                    sensorsFounded++;
                }
            }

            sensorLogDTO.setPagesCount(calulatePagesCount(sensorsFounded));
            sensorLogDTOS.add(sensorLogDTO);
        }

        return sensorLogDTOS;
    }

    private List<Map.Entry<Timestamp, Double>> sensorsToPairs(List<Sensor> sensors) {
        List<Map.Entry<Timestamp, Double>> sensorPairs = new LinkedList<Map.Entry<Timestamp, Double>>();

        for (Sensor sensor : sensors) {
            sensorPairs.add(new AbstractMap.SimpleEntry<Timestamp, Double>(sensor.getArriveTime(), sensor.getValue()));
        }

        return sensorPairs;
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
