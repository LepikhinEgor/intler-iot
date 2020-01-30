package intler_iot.controllers.entities;

import javafx.util.Pair;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SensorLog {
    private String sensorName;
    private List<Pair<Timestamp, Double>> sensorsLogs;

    public SensorLog() {
        sensorsLogs = new ArrayList<>();
    }

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

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public List<Pair<Timestamp, Double>> getSensorsLogs() {
        return sensorsLogs;
    }

    public void setSensorsLogs(List<Pair<Timestamp, Double>> sensorsLogs) {
        this.sensorsLogs = sensorsLogs;
    }
}
