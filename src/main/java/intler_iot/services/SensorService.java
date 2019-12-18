package intler_iot.services;

import intler_iot.controllers.entities.SensorsData;
import intler_iot.dao.SensorDao;
import intler_iot.dao.entities.Device;
import intler_iot.dao.entities.Sensor;
import intler_iot.dao.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SensorService {

    private DeviceService deviceService;
    private UserService userService;

    private SensorDao sensorDao;

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
        User user = userService.getUserByLogin(sensorsData.getLogin());
        deviceService.connectDevice(sensorsData.getLogin(), sensorsData.getPassword(), sensorsData.getDeviceName(), sensorsData.getDeviceType());
        Device device = deviceService.getDeviceById(user, sensorsData.getDeviceName());

        recordSensorsValues(device, sensorsData.getSensorsValue());
        removeOldSensorsValue(device);
    }

    public void recordSensorsValues(Device device, HashMap<String, Double> sensorsValues) {
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

    public void removeOldSensorsValue(Device device) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis() - 1000*60*60);
        sensorDao.removeOldValues(device, timestamp);
    }


}
