package intler_iot.services;

import intler_iot.controllers.entities.OrderData;
import intler_iot.dao.CloudOrderDao;
import intler_iot.dao.entities.CloudOrder;
import intler_iot.dao.entities.Device;
import intler_iot.dao.entities.User;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;

public class CloudOrderService {

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

    public void recordNewOrder(OrderData orderData) {
        User user = userService.getUserByLogin(orderData.getLogin());
        Device device = deviceService.getDeviceById(user, orderData.getDeviceName());

        CloudOrder order = new CloudOrder();
        order.setName(orderData.getName());
        order.setValue(orderData.getValue());
        order.setDevice(device);

        cloudOrderDao.save(order);
    }

}
