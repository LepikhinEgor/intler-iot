package intler_iot.controllers.entities;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Objects;

public class SensorLog {
    private String sensorName;
    private HashMap<Timestamp, Double> sensorsLogs;

    @Override
    public String toString() {
        return "SensorLog{" +
                "sensorName='" + sensorName + '\'' +
                ", sensorsLogs=" + sensorsLogs +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SensorLog sensorLog = (SensorLog) o;
        return Objects.equals(sensorName, sensorLog.sensorName) &&
                Objects.equals(sensorsLogs, sensorLog.sensorsLogs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sensorName, sensorsLogs);
    }

    public HashMap<Timestamp, Double> getSensorsLogs() {
        return sensorsLogs;
    }

    public void setSensorsLogs(HashMap<Timestamp, Double> sensorsLogs) {
        this.sensorsLogs = sensorsLogs;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }
}
