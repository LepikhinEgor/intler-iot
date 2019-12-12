package intler_iot.services;


import intler_iot.dao.DeviceDao;
import intler_iot.dao.entities.Device;
import intler_iot.dao.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void connectDevice(String login, String password, String deviceName, String deviceType) {
        User owner = userService.getUserByLogin(login);

        Device device = new Device();
        device.setName(deviceName);
        device.setType(deviceType);
        device.setOwner(owner);

        deviceDao.connectDevice(device);
    }

    public Device getDeviceById(User user, String deviceName) {

        Device foundDevice = deviceDao.getUserDeviceByName(deviceName, user);

        return foundDevice;
    }
}
