package intler_iot.services;

import intler_iot.controllers.entities.SensorPageDTO;
import intler_iot.controllers.entities.DeviceStateDTO;
import intler_iot.dao.SensorDao;
import intler_iot.dao.entities.Device;
import intler_iot.dao.entities.SensorValue;
import intler_iot.dao.entities.User;
import intler_iot.services.converters.dto.SensorPageDTOConverter;
import intler_iot.services.exceptions.NotAuthException;
import intler_iot.services.exceptions.SensorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

@Service
public class SensorService {

    static final int SENSORS_ON_PAGE = 10;
    static final int LAST_PAGE_NUM = -1;

    private SensorPageDTOConverter sensorPageDTOConverter;

    private DeviceService deviceService;
    private UserService userService;

    private SensorDao sensorDao;

    private static final long DAY_IN_MILLIS = 1000*60*60*24;
    private static final long SENSORS_HOLD_DAYS = 1;

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

    /**
     * Save sensors values from device data
     * @param deviceStateDTO sensors data from device
     * @throws NotAuthException
     */
    @Transactional
    public void updateSensorsValues(DeviceStateDTO deviceStateDTO) throws NotAuthException {
        User user = userService.authUser(deviceStateDTO.getLogin(), deviceStateDTO.getPassword());
        deviceService.connectDevice(deviceStateDTO.getLogin(), deviceStateDTO.getPassword(), deviceStateDTO.getDeviceName(), deviceStateDTO.getDeviceType());
        Device device = deviceService.getDeviceById(user, deviceStateDTO.getDeviceName());

        recordSensorsValues(device, deviceStateDTO.getSensorsValue());
    }

    /**
     * Wrap device data to Sensor and save all new sensors
     * @param device
     * @param sensorsValues
     */
    private void recordSensorsValues(Device device, HashMap<String, Double> sensorsValues) {
        List<SensorValue> sensorValues = new ArrayList<SensorValue>();

        for (String sensorName: sensorsValues.keySet()) {
            SensorValue sensorValue = new SensorValue();
            sensorValue.setName(sensorName);
            sensorValue.setValue(sensorsValues.get(sensorName));
            sensorValue.setDevice(device);

            sensorValues.add(sensorValue);
        }

        sensorDao.recordAll(sensorValues);
    }

    /**
     * @return Map with sensors name from devices. Key contains device name, Value contains list of sensors name of this device
     */
    @Transactional
    public HashMap<String, Collection<String>> getSensorsName() {
        User user = userService.getCurrentUser();
        List<Device> userDevices = deviceService.getUserDevices(user);

        HashMap<String, Collection<String>> devicesSensors = new HashMap<>();

        for (Device device : userDevices) {
            Collection<String> unicSensorsName = getUnicSensorNames(device.getSensorValues());
            devicesSensors.put(device.getName(), unicSensorsName);
        }

        return devicesSensors;
    }

    /**
     * Method remove old sensors value, which arrive time old the defined bound
     */
    @Transactional
    public void removeOldSensorsValue() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis() - DAY_IN_MILLIS * SENSORS_HOLD_DAYS);
        sensorDao.removeOldValues(timestamp);
    }

    /**
     *
     * @param user
     * @return List of last user sensors with unique names from all devices
     */
    @Transactional
    public List<SensorValue> getLastSensors(User user) {
        List<SensorValue> lastSensorValues = sensorDao.getLastSensors(user);

        return lastSensorValues;
    }

    /**
     * Method get all user sensors value and sort its by name. After that getting first SENSORS_ON_PAGE sensor values and wrap its to SensorPageDTO
     * @return all unique sensors as first table page.
     * @throws NotAuthException
     */
    @Transactional
    public List<SensorPageDTO> getUserSensors() throws NotAuthException {
        User user = userService.getCurrentUser();
        List<Device> userDevices = deviceService.getUserDevices(user);

        List<SensorValue> sensorValues = sensorDao.getAll(userDevices);
        List<SensorPageDTO> sensorsPage = transformToSensorsPage(sensorValues,userDevices);

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
    @Transactional
    public SensorPageDTO getSensorLogPage(String sensorName, int pageNum) throws NotAuthException, SensorNotFoundException {
        User user = userService.getCurrentUser();
        List<SensorValue> sensorValues = sensorDao.getSensorValues(sensorName, user);

        if (sensorValues.size() == 0)
            throw new SensorNotFoundException("Requested sensor page is empty, because not found sensor values from db. Propably " +
                    "sensor data was auto deleted and not updated");

        int pagesCount = calulatePagesCount(sensorValues.size());
        if (pageNum == LAST_PAGE_NUM || pageNum > pagesCount)
            pageNum = pagesCount -1;

        List<SensorValue> sensorsPage = pullSensorPage(sensorValues, pageNum);

        SensorPageDTO sensorPageDTO = sensorPageDTOConverter.covertToDTO(sensorName, pageNum, pagesCount, sensorsPage);

        return sensorPageDTO;
    }

    /**
     * Get sensor values on specify page. This method replase List.subList(), because actual sensors value may be lass then page bounds.
     * Otherwise method List.subList() will throw indexOfBoundException
     * @param sensorValues
     * @param pageNum
     * @return
     */
    public List<SensorValue> pullSensorPage(List<SensorValue> sensorValues, int pageNum) {
        sensorValues = sortSensors(sensorValues);

        List<SensorValue> sensorsPage = new LinkedList<>();

        int firstSensor = pageNum * SENSORS_ON_PAGE;
        int lastSensor = firstSensor + SENSORS_ON_PAGE;

        for (int i = firstSensor; i < sensorValues.size() && i < lastSensor; i++) {
                sensorsPage.add(sensorValues.get(i));
        }

        return sensorsPage;
    }

    private List<SensorValue> sortSensors(List<SensorValue> sensorValues) {
        sensorValues.sort((SensorValue s1, SensorValue s2) -> s2.getArriveTime().compareTo(s1.getArriveTime()));

        return sensorValues;
    }

    /**
     * Method iterates over sensors and take first N of them to SensorPageDTO for each unique sensor name
     * @param sensors
     * @param devices
     * @return
     */
    private List<SensorPageDTO> transformToSensorsPage(List<SensorValue> sensors, List<Device> devices) {
        Collection<String> unicSensors = getUnicSensorNames(sensors);
        List<SensorPageDTO> sensorPageDTOs = new ArrayList<>();

        sensors = sortSensors(sensors);

        for (String name : unicSensors) {
            List<SensorValue> sensorValueValues = new LinkedList<>();

            int sensorsFounded = 0;
            for (SensorValue sensorValue : sensors) {
                if (sensorValue.getName().equals(name)) {
                    if (sensorValueValues.size() < SENSORS_ON_PAGE) {
                        sensorValueValues.add(sensorValue);
                    }
                    sensorsFounded++;
                }
            }

            int pagesCount =  calulatePagesCount(sensorsFounded);
            SensorPageDTO sensorPageDTO = sensorPageDTOConverter.covertToDTO(name, 0,pagesCount, sensorValueValues);
            sensorPageDTOs.add(sensorPageDTO);
        }

        return sensorPageDTOs;
    }


    /**
     * @param sensorValues
     * @return Collection of unique sensors name
     */
    private Collection<String> getUnicSensorNames(List<SensorValue> sensorValues) {
        HashSet<String> unicNames = new HashSet<>();

        for (SensorValue sensorValue : sensorValues)
            unicNames.add(sensorValue.getName());

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
