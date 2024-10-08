package intler_iot.services;


import intler_iot.dao.DeviceDao;
import intler_iot.dao.entities.Device;
import intler_iot.dao.entities.User;
import intler_iot.services.exceptions.NotAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
public class DeviceService {

    private DeviceDao deviceDao;

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setDeviceDao(DeviceDao deviceDao) {
        this.deviceDao = deviceDao;
    }

    @Transactional
    public void connectDevice(String login, String password, String deviceName, String deviceType) throws NotAuthException {
        User owner = userService.authUser(login, password);

        Device device = new Device();
        device.setName(deviceName);
        device.setType(deviceType);
        device.setOwner(owner);
        device.setLastDeviceMessageTime(new Timestamp(System.currentTimeMillis()));

        deviceDao.connectDevice(device);
    }

    @Transactional
    public Device getDeviceById(User user, String deviceName) {
        Device foundDevice = deviceDao.getUserDeviceByName(deviceName, user);

        return foundDevice;
    }

    @Transactional
    public List<Device> getUserDevices(User user) {
        List<Device> devices = deviceDao.getUserDevices(user);

        return devices;
    }
}
