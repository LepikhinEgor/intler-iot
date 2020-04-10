package intler_iot.services;

import intler_iot.controllers.entities.OrderDTO;
import intler_iot.controllers.entities.DeviceStateDTO;
import intler_iot.dao.CloudOrderDao;
import intler_iot.dao.entities.CloudOrder;
import intler_iot.dao.entities.Device;
import intler_iot.dao.entities.User;
import intler_iot.services.converters.dto.CloudOrderDTOConverter;
import intler_iot.services.exceptions.NotAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@Service
public class CloudOrderService {

    private CloudOrderDTOConverter cloudOrderDTOConverter;

    private UserService userService;
    private DeviceService deviceService;

    private CloudOrderDao cloudOrderDao;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setDeviceService(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Autowired
    public void setCloudOrderDao(CloudOrderDao cloudOrderDao) {
        this.cloudOrderDao = cloudOrderDao;
    }

    @Autowired
    public void setCloudOrderDTOConverter(CloudOrderDTOConverter cloudOrderDTOConverter) {
        this.cloudOrderDTOConverter = cloudOrderDTOConverter;
    }

    @Transactional
    public void recordNewOrder(OrderDTO orderDTO) {
        User user = userService.getCurrentUser();
        Device device = deviceService.getDeviceById(user, orderDTO.getDeviceName());

        CloudOrder order = cloudOrderDTOConverter.convertToDomain(orderDTO, device);

        cloudOrderDao.save(order);
    }

    @Transactional
    public HashMap<String, Double> getDeviceOrders(String deviceName, String login, String password) {
        User user = userService.authUser(login, password);
        Device device = deviceService.getDeviceById(user, deviceName);

        List<CloudOrder> orders = cloudOrderDao.getDeviceOrders(device);

        HashMap<String, Double> ordersDTO = cloudOrderDTOConverter.convertToDTO(orders);
        return ordersDTO;
    }

    @Transactional
    public void deleteOldOrders() {
        cloudOrderDao.deleteOld();
    }

    @Transactional
    public void markOldOrdersAsRemoved(DeviceStateDTO deviceStateDTO) {
        User user = userService.authUser(deviceStateDTO.getLogin(), deviceStateDTO.getPassword());
        Device device = deviceService.getDeviceById(user, deviceStateDTO.getDeviceName());

        if (deviceStateDTO.getOrdersAccepted().size() == 0)
            return;

        cloudOrderDao.markRemoved(deviceStateDTO.getOrdersAccepted(), device);
    }

}
