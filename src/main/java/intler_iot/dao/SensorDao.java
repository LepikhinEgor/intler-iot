package intler_iot.dao;

import intler_iot.dao.entities.Device;
import intler_iot.dao.entities.SensorValue;
import intler_iot.dao.entities.User;

import java.sql.Timestamp;
import java.util.List;

public abstract class SensorDao {
    public abstract void create(SensorValue sensorValue);
    public abstract void recordAll(List<SensorValue> sensorValues);
    public abstract void removeOldValues(Timestamp deadline);
    public abstract List<SensorValue> getAll(List<Device> userDevices);
    public abstract List<SensorValue> getSensorValues(String sensorName, User user);
    public abstract List<SensorValue> getLastSensors(User user);
}
