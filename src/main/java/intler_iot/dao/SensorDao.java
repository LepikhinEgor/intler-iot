package intler_iot.dao;

import intler_iot.dao.entities.Sensor;

import java.util.List;

public abstract class SensorDao {
    public abstract void create(Sensor sensor);
    public abstract void recordAll(List<Sensor> sensors);
}
