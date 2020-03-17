package intler_iot.services;

import intler_iot.controllers.entities.DeviceStateDTO;
import intler_iot.controllers.entities.SensorPageDTO;
import intler_iot.dao.SensorDao;
import intler_iot.dao.entities.Device;
import intler_iot.dao.entities.Sensor;
import intler_iot.dao.entities.User;
import intler_iot.services.converters.dto.SensorPageDTOConverter;
import intler_iot.services.exceptions.NotAuthException;
import intler_iot.services.exceptions.SensorNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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

    @Spy
    private SensorPageDTOConverter sensorPageDTOConverterSpy;

    @Before
    public void initTest() {
        sensorService = new SensorService();
        MockitoAnnotations.initMocks(this);
    }

    private void inject() {
        sensorService.setDeviceService(deviceServiceMock);
        sensorService.setSensorDao(sensorDaoMock);
        sensorService.setUserService(userServiceMock);
        sensorService.setSensorPageDTOConverter(sensorPageDTOConverterSpy);
    }

    private DeviceStateDTO getValidSensorData() {
        DeviceStateDTO sensorsData = new DeviceStateDTO();
        sensorsData.setLogin("admin");
        sensorsData.setPassword("qwerty");
        sensorsData.setDeviceName("MyArduino");
        sensorsData.setDeviceType("nano");

        HashMap<String, Double> sensorsVal = new HashMap<>();
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

    private List<Sensor> getSensorsList(int size) {
        List<Sensor> sensors = new ArrayList<>();
        DeviceStateDTO sensorsData = getValidSensorData();

        for(int i = 0; i < size; i++) {
            Sensor sensor = new Sensor();
            sensor.setName("sensor");
            sensor.setValue(0);
            sensor.setDevice(getValidDevice());
            sensor.setArriveTime(new Timestamp(System.currentTimeMillis()));

            sensors.add(sensor);
        }

        return sensors;
    }

    @Test
    public void updateSensorsValues_successUpdated() throws NotAuthException {
        User user = getValidUser();
        Device device = getValidDevice();
        device.setOwner(user);

        when(userServiceMock.authUser(any(), any())).thenReturn(user);
        when(deviceServiceMock.getDeviceById(user, device.getName())).thenReturn(device);
        doNothing().when(deviceServiceMock).connectDevice(user.getLogin(), user.getPassword(), device.getName(), device.getType());
        doNothing().when(sensorDaoMock).recordAll(getSensorsList(23));
        inject();

        sensorService.updateSensorsValues(getValidSensorData());
        assert(true);
    }

    @Test(expected = NotAuthException.class)
    public void updateSensorsValues_failByAuthUser() throws NotAuthException {
        User user = getValidUser();
        Device device = getValidDevice();
        device.setOwner(user);

        when(userServiceMock.authUser(any(), any())).thenThrow(new NotAuthException());

        inject();

        sensorService.updateSensorsValues(getValidSensorData());
        assert(true);
    }

    @Test
    public void getSensorLogPage_expected5From15items() throws NotAuthException, SensorNotFoundException {
        String sensorName = "temp";
        int pageNum = 1;

        when(sensorDaoMock.getSensorValues(any(), any())).thenReturn(getSensorsList(15));
        when(userServiceMock.getCurrentUser()).thenReturn(getValidUser());
        inject();

        SensorPageDTO pageSensors = sensorService.getSensorLogPage(sensorName, pageNum);

        assertEquals(5, pageSensors.getSensorsLogs().size());
    }

    @Test
    public void getSensorLogPage_outOfBoundsPage_returnLastPage() throws NotAuthException, SensorNotFoundException {
        String sensorName = "temp";
        int pageNum = 5;

        when(sensorDaoMock.getSensorValues(any(), any())).thenReturn(getSensorsList(15));
        when(userServiceMock.getCurrentUser()).thenReturn(getValidUser());
        inject();

        SensorPageDTO pageSensors = sensorService.getSensorLogPage(sensorName, pageNum);

        assertEquals(5, pageSensors.getSensorsLogs().size());
    }

    @Test(expected = SensorNotFoundException.class)
    public void getSensorLogPage_sensorValuesEmpty_throwException() throws NotAuthException, SensorNotFoundException {
        String sensorName = "temp";
        int pageNum = 5;

        when(sensorDaoMock.getSensorValues(any(), any())).thenReturn(getSensorsList(0));
        when(userServiceMock.getCurrentUser()).thenReturn(getValidUser());
        inject();

        SensorPageDTO pageSensors = sensorService.getSensorLogPage(sensorName, pageNum);

        assertEquals(5, pageSensors.getSensorsLogs().size());
    }
}
