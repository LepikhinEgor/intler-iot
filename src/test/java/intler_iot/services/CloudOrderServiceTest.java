package intler_iot.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import intler_iot.controllers.entities.DeviceStateDTO;
import intler_iot.controllers.entities.OrderDTO;
import intler_iot.dao.CloudOrderDao;
import intler_iot.dao.entities.CloudOrder;
import intler_iot.dao.entities.Device;
import intler_iot.services.converters.dto.CloudOrderDTOConverter;
import intler_iot.services.exceptions.NotAuthException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashMap;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CloudOrderServiceTest {

    @Mock
    private CloudOrderDTOConverter cloudOrderDTOConverter;

    @Mock
    private UserService userService;

    @Mock
    private  DeviceService deviceService;

    @Mock
    private CloudOrderDao cloudOrderDao;

    @InjectMocks
    CloudOrderService cloudOrderService = new CloudOrderService();

    @Test
    public void recordNewOrder_successRecord() throws NotAuthException {
        OrderDTO orderDTO = new OrderDTO();
        when(cloudOrderDTOConverter.convertToDomain(any(), any())).thenReturn(new CloudOrder());

        cloudOrderService.recordNewOrder(orderDTO);
        verify(cloudOrderDao).save(any(CloudOrder.class));
    }

    @Test
    public void getDeviceOrders_successReturnOrders() {
        cloudOrderService.getDeviceOrders("device", "login", "password");

        verify(cloudOrderDao).getDeviceOrders(any(Device.class));
        verify(cloudOrderDTOConverter).convertToDTO(anyListOf(CloudOrder.class));
    }

    @Test
    public void markOldOrdersAsRemoved_successRemove() {
        DeviceStateDTO deviceStateDTO = new DeviceStateDTO();
        deviceStateDTO.setOrdersAccepted(Arrays.asList("order1", "order2"));

        cloudOrderService.markOldOrdersAsRemoved(deviceStateDTO);

        verify(cloudOrderDao).markRemoved(any(), any());
    }

    @Test
    public void markOldOrdersAsRemoved_notRemoveEmptyList() {
        DeviceStateDTO deviceStateDTO = new DeviceStateDTO();
        deviceStateDTO.setOrdersAccepted(Arrays.asList());

        cloudOrderService.markOldOrdersAsRemoved(deviceStateDTO);

        verify(cloudOrderDao, never()).markRemoved(any(), any());
    }

}
