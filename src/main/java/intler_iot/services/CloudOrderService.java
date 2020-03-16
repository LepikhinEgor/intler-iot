package intler_iot.services;

import intler_iot.controllers.entities.OrderDTO;
import intler_iot.controllers.entities.DeviceStateDTO;
import intler_iot.dao.CloudOrderDao;
import intler_iot.dao.entities.CloudOrder;
import intler_iot.dao.entities.Device;
import intler_iot.dao.entities.User;
import intler_iot.services.exceptions.NotAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public void recordNewOrder(OrderDTO orderDTO) throws NotAuthException {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Device device = deviceService.getDeviceById(user, orderDTO.getDeviceName());

        CloudOrder order = new CloudOrder();
        order.setKeyWard(orderDTO.getKeyWard());
        order.setValue(orderDTO.getValue());
        order.setDevice(device);
        order.setTiming(new Timestamp(System.currentTimeMillis()));
        order.setUsed(false);
        order.setRemoved(false);

        cloudOrderDao.save(order);
    }

    public HashMap<String, Double> getDeviceOrders(String deviceName, String login, String password) throws NotAuthException {
        User user = userService.authUser(login, password);
        Device device = deviceService.getDeviceById(user, deviceName);

        List<CloudOrder> orders = cloudOrderDao.getDeviceOrders(device);

        return ordersToMap(orders);
    }

    public void deleteOldOrders() {
        cloudOrderDao.deleteOld();
    }

    public void markOldOrdersAsRemoved(DeviceStateDTO deviceStateDTO) throws NotAuthException {
        User user = userService.authUser(deviceStateDTO.getLogin(), deviceStateDTO.getPassword());
        Device device = deviceService.getDeviceById(user, deviceStateDTO.getDeviceName());

        if (deviceStateDTO.getOrdersAccepted().size() == 0)
            return;

        cloudOrderDao.markRemoved(deviceStateDTO.getOrdersAccepted(), device);
    }

    private HashMap<String, Double> ordersToMap(List<CloudOrder> ordersList) {
        HashMap<String, Double> ordersMap = new HashMap<>();
        for (CloudOrder order: ordersList) {
            ordersMap.put(order.getKeyWard(), order.getValue());
        }

        return ordersMap;
    }

}
