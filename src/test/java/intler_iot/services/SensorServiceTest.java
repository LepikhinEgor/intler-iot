package intler_iot.services;

import intler_iot.controllers.entities.SensorsData;
import intler_iot.dao.SensorDao;
import intler_iot.dao.entities.Device;
import intler_iot.dao.entities.Sensor;
import intler_iot.dao.entities.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.*;

public class SensorServiceTest {

    private SensorService sensorService;

    @Mock
    private UserService userServiceMock;
    @Mock
    private DeviceService deviceServiceMock;

    @Mock
    private SensorDao sensorDaoMock;

    @Before
    public void initTest() {
        sensorService = new SensorService();
        MockitoAnnotations.initMocks(this);
    }

    private void inject() {
        sensorService.setDeviceService(deviceServiceMock);
        sensorService.setSensorDao(sensorDaoMock);
        sensorService.setUserService(userServiceMock);
    }

    private SensorsData getValidSensorData() {
        SensorsData sensorsData = new SensorsData();
        sensorsData.setLogin("admin");
        sensorsData.setPassword("qwerty");
        sensorsData.setDeviceName("MyArduino");
        sensorsData.setDeviceType("nano");

        HashMap<String, Double> sensorsVal = new HashMap<>();
        sensorsVal.put("sensor1" , 1D);
        sensorsVal.put("sensor2" , 2D);
        sensorsVal.put("sensor3" , 3D);
        sensorsData.setSensorsValue(sensorsVal);

        return sensorsData;
    }

    private User getValidUser() {
        User user = new User();
        user.setPassword("qwerty");
        user.setLogin("admin");
        user.setEmail("admin@mail.ru");
        user.setId(1);

        return user;
    }

    private Device getValidDevice() {
       Device device = new Device();
       device.setType("nano");
       device.setName("MyArduino");
       device.setId(1);
       device.setOwner(getValidUser());

       return device;
    }

    private List<Sensor> getSensorsList() {
        List<Sensor> sensors = new ArrayList<>();
        SensorsData sensorsData = getValidSensorData();

        for (String sensorName: sensorsData.getSensorsValue().keySet()) {
            Sensor sensor = new Sensor();
            sensor.setName(sensorName);
            sensor.setValue(sensorsData.getSensorsValue().get(sensorName));
            sensor.setDevice(getValidDevice());

            sensors.add(sensor);
        }

        return sensors;
    }

    @Test
    public void updateSensorsValues_successUpdated() {
        User user = getValidUser();
        Device device = getValidDevice();
        device.setOwner(user);

        when(userServiceMock.getUserByLogin(user.getLogin())).thenReturn(user);
        when(deviceServiceMock.getDeviceById(user, device.getName())).thenReturn(device);
        doNothing().when(deviceServiceMock).connectDevice(user.getLogin(), user.getPassword(), device.getName(), device.getType());
        doNothing().when(sensorDaoMock).recordAll(getSensorsList());
        inject();

        sensorService.updateSensorsValues(getValidSensorData());
        assert(true);
    }
}
