package intler_iot.services;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        System.out.println("1 test");
        User user = userService.authUser(sensorsData.getLogin(), sensorsData.getPassword());
        System.out.println("2 test");
        deviceService.connectDevice(sensorsData.getLogin(), sensorsData.getPassword(), sensorsData.getDeviceName(), sensorsData.getDeviceType());
        System.out.println("3 test");
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
}
