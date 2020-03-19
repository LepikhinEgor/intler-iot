package intler_iot.services;

import intler_iot.dao.DeviceDao;
import intler_iot.dao.entities.Device;
import intler_iot.services.exceptions.NotAuthException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DeviceServiceTest {

    @Mock
    private DeviceDao deviceDao;

    @Mock
    private UserService userService;

    @InjectMocks
    private DeviceService deviceService = new DeviceService();


    @Test
    public void connectDevice_successConnect() throws NotAuthException {
        deviceService.connectDevice("login", "password", "arduino", "arduino");

        verify(deviceDao).connectDevice(any(Device.class));
        assertTrue(true);
    }

    @Test(expected = NotAuthException.class)
    public void connectDevice_thenThrowNotAuthException() throws NotAuthException {
        when(userService.authUser(any(), any())).thenThrow(new NotAuthException());

        deviceService.connectDevice("login", "password", "arduino", "arduino");
    }
}
