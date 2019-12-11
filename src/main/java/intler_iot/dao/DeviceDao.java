package intler_iot.dao;

import intler_iot.dao.entities.Device;

public abstract class DeviceDao {
    public abstract void connectDevice(Device device);
}
