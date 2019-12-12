package intler_iot.services;

import intler_iot.controllers.entities.SensorsData;
import intler_iot.dao.SensorDao;
import intler_iot.dao.entities.Device;
import intler_iot.dao.entities.Sensor;
import intler_iot.dao.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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



    public void receiveSensors(SensorsData sensorsData) {
        List<Sensor> sensors = new ArrayList<Sensor>();

        User user = userService.getUserByLogin(sensorsData.getLogin());
        System.out.println(user);
        deviceService.connectDevice(sensorsData.getLogin(), sensorsData.getPassword(), sensorsData.getDeviceName(), sensorsData.getDeviceType());
        System.out.println("Connecteed");
        Device device = deviceService.getDeviceById(user, sensorsData.getDeviceName());
        System.out.println(device);

        for (String sensorName: sensorsData.getSensorsValue().keySet()) {
            Sensor sensor = new Sensor();
            sensor.setName(sensorName);
            sensor.setValue(sensorsData.getSensorsValue().get(sensorName));
            sensor.setDevice(device);

            sensors.add(sensor);
        }

        sensorDao.recordAll(sensors);
    }


}
