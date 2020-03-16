package intler_iot.controllers.entities;

import java.util.Objects;

public class OrderDTO {
    private String keyWard;
    private double value;
    private String deviceName;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getKeyWard() {
        return keyWard;
    }

    public void setKeyWard(String keyWard) {
        this.keyWard = keyWard;
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
                Objects.equals(keyWard, orderDTO.keyWard) &&
                Objects.equals(deviceName, orderDTO.deviceName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyWard, value, deviceName);
    }

    @Override
    public String toString() {
        return "OrderData{" +
                "keyWard='" + keyWard + '\'' +
                ", value=" + value +
                ", deviceName='" + deviceName + '\'' +
                '}';
    }
}
