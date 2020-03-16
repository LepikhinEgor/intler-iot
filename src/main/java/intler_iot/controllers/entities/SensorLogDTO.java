package intler_iot.controllers.entities;

import java.sql.Timestamp;
import java.util.*;

public class SensorLogDTO {
    private String sensorName;
    private int currentPage;
    private int pagesCount;
    private List<Map.Entry<Timestamp, Double>> sensorsLogs;

    public SensorLogDTO() {
        sensorsLogs = new ArrayList<>();
    }

    public SensorLogDTO(String sensorName, int currentPage, int pagesCount, List<Map.Entry<Timestamp, Double>> sensorsLogs) {
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
        SensorLogDTO sensorLogDTO = (SensorLogDTO) o;
        return currentPage == sensorLogDTO.currentPage &&
                pagesCount == sensorLogDTO.pagesCount &&
                Objects.equals(sensorName, sensorLogDTO.sensorName) &&
                Objects.equals(sensorsLogs, sensorLogDTO.sensorsLogs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sensorName, currentPage, pagesCount, sensorsLogs);
    }
}
