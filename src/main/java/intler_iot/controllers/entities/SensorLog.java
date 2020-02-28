package intler_iot.controllers.entities;

import javafx.util.Pair;

import java.sql.Timestamp;
import java.util.*;

public class SensorLog {
    private String sensorName;
    private int currentPage;
    private int pagesCount;
    private List<Map.Entry<Timestamp, Double>> sensorsLogs;

    public SensorLog() {
        sensorsLogs = new ArrayList<>();
    }

    public SensorLog(String sensorName, int currentPage, int pagesCount, List<Map.Entry<Timestamp, Double>> sensorsLogs) {
        this.sensorName = sensorName;
        this.currentPage = currentPage;
        this.pagesCount = pagesCount;
        this.sensorsLogs = sensorsLogs;
    }

    @Override
    public String toString() {
        return "SensorLog{" +
                "sensorName='" + sensorName + '\'' +
                ", sensorsLogs=" + sensorsLogs +
                '}';
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public List<Map.Entry<Timestamp, Double>> getSensorsLogs() {
        return sensorsLogs;
    }

    public void setSensorsLogs(List<Map.Entry<Timestamp, Double>> sensorsLogs) {
        this.sensorsLogs = sensorsLogs;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SensorLog sensorLog = (SensorLog) o;
        return currentPage == sensorLog.currentPage &&
                pagesCount == sensorLog.pagesCount &&
                Objects.equals(sensorName, sensorLog.sensorName) &&
                Objects.equals(sensorsLogs, sensorLog.sensorsLogs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sensorName, currentPage, pagesCount, sensorsLogs);
    }
}
