package intler_iot.services;

import intler_iot.controllers.entities.SensorLog;
import intler_iot.controllers.entities.SensorsData;
import intler_iot.dao.SensorDao;
import intler_iot.dao.entities.Device;
import intler_iot.dao.entities.Sensor;
import intler_iot.dao.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
@EnableScheduling
public class SensorService {

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

    public void updateSensorsValues(SensorsData sensorsData) {
        User user = userService.authUser(sensorsData.getLogin(), sensorsData.getPassword());
        deviceService.connectDevice(sensorsData.getLogin(), sensorsData.getPassword(), sensorsData.getDeviceName(), sensorsData.getDeviceType());
        Device device = deviceService.getDeviceById(user, sensorsData.getDeviceName());

        recordSensorsValues(device, sensorsData.getSensorsValue());
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

    public void removeOldSensorsValue() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis() - DAY_IN_MILLIS);
        sensorDao.removeOldValues(timestamp);
    }

    public List<SensorLog> getUserSensors(String login, String password) {
        User user = userService.authUser(login, password);
        List<Device> userDevices = deviceService.getUserDevices(user);

        List<Sensor> sensors = sensorDao.getAll(userDevices);
        List<SensorLog> lastUnicSensors = transformToSensorLogs(sensors,userDevices);

        return lastUnicSensors;
    }

    private List<SensorLog> transformToSensorLogs(List<Sensor> sensors, List<Device> devices) {
        //TODO test and optimize and insert device name
        HashSet<String> unicSensors = new HashSet<String>();
        List<SensorLog> sensorLogs = new ArrayList<>();

        for(Sensor sensor : sensors) {
            unicSensors.add(sensor.getName());
        }

        sensors.sort((Sensor s1, Sensor s2) -> s1.getArriveTime().compareTo(s2.getArriveTime()));

        for (String name : unicSensors) {
            SensorLog sensorLog = new SensorLog();
            sensorLog.setSensorName(name);

            int sensorsFounded = 0;
            for (Sensor sensor : sensors) {
                if (sensor.getName().equals(name)) {
                    sensorLog.getSensorsLogs().put(sensor.getArriveTime(), sensor.getValue());
                    sensorsFounded++;
                    if (sensorsFounded >= 10)
                        break;
                }
            }

            sensorLogs.add(sensorLog);
        }

        return sensorLogs;
    }
}
