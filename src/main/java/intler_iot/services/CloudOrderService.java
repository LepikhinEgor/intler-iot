package intler_iot.services;

import intler_iot.controllers.entities.OrderData;
import intler_iot.controllers.entities.SensorsData;
import intler_iot.dao.CloudOrderDao;
import intler_iot.dao.entities.CloudOrder;
import intler_iot.dao.entities.Device;
import intler_iot.dao.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@Service
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
        User user = userService.authUser(orderData.getLogin(), orderData.getPassword());
        Device device = deviceService.getDeviceById(user, orderData.getDeviceName());

        CloudOrder order = new CloudOrder();
        order.setName(orderData.getName());
        order.setValue(orderData.getValue());
        order.setDevice(device);
        order.setTiming(new Timestamp(System.currentTimeMillis()));
        order.setUsed(false);
        order.setRemoved(false);

        cloudOrderDao.save(order);
    }

    public HashMap<String, Double> getDeviceOrders(String deviceName, String login, String password) {
        User user = userService.authUser(login, password);
        Device device = deviceService.getDeviceById(user, deviceName);

        List<CloudOrder> orders = cloudOrderDao.getDeviceOrders(device);

        return ordersToMap(orders);
    }

    public void deleteOldOrders() {
        cloudOrderDao.deleteOld();
    }

    public void markOldOrdersAsRemoved(SensorsData sensorsData) {
        User user = userService.authUser(sensorsData.getLogin(), sensorsData.getPassword());
        Device device = deviceService.getDeviceById(user, sensorsData.getDeviceName());

        if (sensorsData.getOrdersAccepted().size() == 0)
            return;

        cloudOrderDao.markRemoved(sensorsData.getOrdersAccepted(), device);
    }

    private HashMap<String, Double> ordersToMap(List<CloudOrder> ordersList) {
        HashMap<String, Double> ordersMap = new HashMap<>();
        for (CloudOrder order: ordersList) {
            ordersMap.put(order.getName(), order.getValue());
        }

        return ordersMap;
    }

}
