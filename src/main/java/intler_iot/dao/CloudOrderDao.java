package intler_iot.dao;

import intler_iot.dao.entities.CloudOrder;

public abstract class CloudOrderDao {
    public abstract void save(CloudOrder order);
}
