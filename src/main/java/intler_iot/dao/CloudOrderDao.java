package intler_iot.dao;

import intler_iot.dao.entities.CloudOrder;
import intler_iot.dao.entities.Device;

import java.util.List;

public abstract class CloudOrderDao {
    public abstract void save(CloudOrder order);
    public abstract List<CloudOrder> getDeviceOrders(Device device);
    public abstract void deleteOld();
    public abstract void markRemoved(List<String> ordersName, Device device);
}
