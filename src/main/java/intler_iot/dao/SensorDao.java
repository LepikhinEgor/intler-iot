package intler_iot.dao;

import intler_iot.dao.entities.Device;
import intler_iot.dao.entities.Sensor;

import java.sql.Timestamp;
import java.util.List;

public abstract class SensorDao {
    public abstract void create(Sensor sensor);
    public abstract void recordAll(List<Sensor> sensors);
    public abstract void removeOldValues(Timestamp deadline);
}
