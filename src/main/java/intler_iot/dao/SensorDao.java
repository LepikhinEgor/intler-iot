package intler_iot.dao;

import intler_iot.dao.entities.Device;
import intler_iot.dao.entities.Sensor;
import intler_iot.dao.entities.User;

import java.sql.Timestamp;
import java.util.List;

public abstract class SensorDao {
    public abstract void create(Sensor sensor);
    public abstract void recordAll(List<Sensor> sensors);
    public abstract void removeOldValues(Timestamp deadline);
    public abstract List<Sensor> getAll(List<Device> userDevices);
    public abstract List<Sensor> getSensorValues(String sensorName, User user);
    public abstract List<Sensor> getLastSensors(User user);
}
