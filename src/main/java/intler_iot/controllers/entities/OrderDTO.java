package intler_iot.controllers.entities;

import java.util.Objects;

public class OrderDTO {
    private String keyword;
    private double value;
    private String deviceName;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDTO orderDTO = (OrderDTO) o;
        return Double.compare(orderDTO.value, value) == 0 &&
                Objects.equals(keyword, orderDTO.keyword) &&
                Objects.equals(deviceName, orderDTO.deviceName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyword, value, deviceName);
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "keyword='" + keyword + '\'' +
                ", value=" + value +
                ", deviceName='" + deviceName + '\'' +
                '}';
    }
}
