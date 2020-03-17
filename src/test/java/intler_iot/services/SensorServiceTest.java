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
import java.util.*;

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

    private List<Sensor> getSensorsList(String name, int size) {
        List<Sensor> sensors = new ArrayList<>();
        DeviceStateDTO sensorsData = getValidSensorData();

        for(int i = 0; i < size; i++) {
            Sensor sensor = new Sensor();
            sensor.setName(name);
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
        doNothing().when(sensorDaoMock).recordAll(getSensorsList("sensor",23));
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
        int sensorsCount = SensorService.SENSORS_ON_PAGE + 5;

        when(sensorDaoMock.getSensorValues(any(), any())).thenReturn(getSensorsList("sensor",sensorsCount));
        when(userServiceMock.getCurrentUser()).thenReturn(getValidUser());
        inject();

        SensorPageDTO pageSensors = sensorService.getSensorLogPage(sensorName, pageNum);

        assertEquals(5, pageSensors.getSensorsLogs().size());
    }

    @Test
    public void getSensorLogPage_expectedMaxByPageFrom15items() throws NotAuthException, SensorNotFoundException {
        String sensorName = "temp";
        int pageNum = 0;

        when(sensorDaoMock.getSensorValues(any(), any())).thenReturn(getSensorsList("sensor",15));
        when(userServiceMock.getCurrentUser()).thenReturn(getValidUser());
        inject();

        SensorPageDTO pageSensors = sensorService.getSensorLogPage(sensorName, pageNum);

        assertEquals(SensorService.SENSORS_ON_PAGE, pageSensors.getSensorsLogs().size());
    }

    @Test
    public void getSensorLogPage_outOfBoundsPage_returnLastPage() throws NotAuthException, SensorNotFoundException {
        String sensorName = "temp";
        int pageNum = 5;

        when(sensorDaoMock.getSensorValues(any(), any())).thenReturn(getSensorsList("sensor",15));
        when(userServiceMock.getCurrentUser()).thenReturn(getValidUser());
        inject();

        SensorPageDTO pageSensors = sensorService.getSensorLogPage(sensorName, pageNum);

        assertEquals(5, pageSensors.getSensorsLogs().size());
    }

    @Test(expected = SensorNotFoundException.class)
    public void getSensorLogPage_sensorValuesEmpty_throwException() throws NotAuthException, SensorNotFoundException {
        String sensorName = "temp";
        int pageNum = 5;

        when(sensorDaoMock.getSensorValues(any(), any())).thenReturn(getSensorsList(sensorName,0));
        when(userServiceMock.getCurrentUser()).thenReturn(getValidUser());
        inject();

        SensorPageDTO pageSensors = sensorService.getSensorLogPage(sensorName, pageNum);

        assertEquals(5, pageSensors.getSensorsLogs().size());
    }

    @Test
    public void getUserSensors_successReturnThreeFullPage() throws NotAuthException {
        List<Sensor> userSensors = new LinkedList<>();
        userSensors.addAll(getSensorsList("sensor1", 15));
        userSensors.addAll(getSensorsList("sensor2", 25));
        userSensors.addAll(getSensorsList("sensor3", 30));

        when(sensorDaoMock.getAll(any())).thenReturn(userSensors);
        when(userServiceMock.getCurrentUser()).thenReturn(getValidUser());
        inject();

        List<SensorPageDTO> pageSensors = sensorService.getUserSensors();

        assertEquals(3, pageSensors.size());
        assertEquals(10, pageSensors.get(0).getSensorsLogs().size());
        assertEquals(10, pageSensors.get(1).getSensorsLogs().size());
        assertEquals(10, pageSensors.get(2).getSensorsLogs().size());
    }

    @Test
    public void getUserSensors_successReturnHalfFilledPage() throws NotAuthException {
        List<Sensor> userSensors = new LinkedList<>();
        userSensors.addAll(getSensorsList("sensor1", 5));

        when(sensorDaoMock.getAll(any())).thenReturn(userSensors);
        when(userServiceMock.getCurrentUser()).thenReturn(getValidUser());
        inject();

        List<SensorPageDTO> pageSensors = sensorService.getUserSensors();

        assertEquals(1, pageSensors.size());
        for (SensorPageDTO sensorPageDTOL : pageSensors) {
            if (sensorPageDTOL.getSensorName().equals("sensor1"))
                assertEquals(5, sensorPageDTOL.getSensorsLogs().size());
        }
    }

    @Test
    public void getUserSensors_successReturn3PagesFromRandomSensorList() throws NotAuthException {
        List<Sensor> userSensors = new LinkedList<>();
        userSensors.addAll(getSensorsList("sensor1", 1));
        userSensors.addAll(getSensorsList("sensor2", 1));
        userSensors.addAll(getSensorsList("sensor3", 1));
        userSensors.addAll(getSensorsList("sensor2", 1));
        userSensors.addAll(getSensorsList("sensor2", 1));
        userSensors.addAll(getSensorsList("sensor3", 1));
        userSensors.addAll(getSensorsList("sensor1", 1));
        userSensors.addAll(getSensorsList("sensor2", 1));
        userSensors.addAll(getSensorsList("sensor3", 1));

        when(sensorDaoMock.getAll(any())).thenReturn(userSensors);
        when(userServiceMock.getCurrentUser()).thenReturn(getValidUser());
        inject();

        List<SensorPageDTO> pageSensors = sensorService.getUserSensors();

        assertEquals(3, pageSensors.size());
        for (SensorPageDTO sensorPageDTOL : pageSensors) {
            if (sensorPageDTOL.getSensorName().equals("sensor1"))
                assertEquals(2, sensorPageDTOL.getSensorsLogs().size());
            if (sensorPageDTOL.getSensorName().equals("sensor2"))
                assertEquals(4, sensorPageDTOL.getSensorsLogs().size());
            if (sensorPageDTOL.getSensorName().equals("sensor3"))
                assertEquals(3, sensorPageDTOL.getSensorsLogs().size());
        }
    }

    @Test
    public void getUserSensors_successReturnZeroPages() throws NotAuthException {
        List<Sensor> userSensors = new LinkedList<>();

        when(sensorDaoMock.getAll(any())).thenReturn(userSensors);
        when(userServiceMock.getCurrentUser()).thenReturn(getValidUser());
        inject();

        List<SensorPageDTO> pageSensors = sensorService.getUserSensors();

        assertEquals(0, pageSensors.size());
    }

    @Test
    public void getSensorsName_return2DevicesWith1and2Sensors() {
        List<Sensor> deviceSensors1 = getSensorsList("sensorName", 5);
        deviceSensors1.addAll(getSensorsList("sensor2",3));
        List<Sensor> deviceSensors2 = getSensorsList("sensorName", 5);
        List<Device> userDevices = new LinkedList<>();

        Device device1 = getValidDevice();
        device1.setName("device1");
        device1.setSensors(deviceSensors1);
        Device device2 = getValidDevice();
        device2.setName("device2");
        device2.setSensors(deviceSensors2);

        userDevices.add(device1);
        userDevices.add(device2);

        when(deviceServiceMock.getUserDevices(any())).thenReturn(userDevices);
        when(userServiceMock.getCurrentUser()).thenReturn(getValidUser());
        inject();

        HashMap<String, Collection<String>> pageSensors = sensorService.getSensorsName();

        assertEquals(2, pageSensors.keySet().size());
        assertEquals(2, pageSensors.get("device1").size());
        assertEquals(1, pageSensors.get("device2").size());
    }

    @Test
    public void getSensorsName_returnZeroDevice() {
        List<Device> userDevices = new LinkedList<>();

        when(deviceServiceMock.getUserDevices(any())).thenReturn(userDevices);
        when(userServiceMock.getCurrentUser()).thenReturn(getValidUser());
        inject();

        HashMap<String, Collection<String>> pageSensors = sensorService.getSensorsName();

        assertEquals(0, pageSensors.keySet().size());
    }
}
