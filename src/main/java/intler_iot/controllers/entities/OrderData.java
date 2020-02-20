package intler_iot.controllers.entities;

import java.util.Objects;

public class OrderData {
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
        OrderData orderData = (OrderData) o;
        return Double.compare(orderData.value, value) == 0 &&
                Objects.equals(keyWard, orderData.keyWard) &&
                Objects.equals(deviceName, orderData.deviceName);
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
