package intler_iot.dao;

import intler_iot.dao.entities.Device;
import intler_iot.dao.entities.User;

import java.util.List;

public abstract class DeviceDao {
    public abstract void connectDevice(Device device);
    public abstract Device getUserDeviceByName(String name, User user);
    public abstract List<Device> getUserDevices(User user);
}
