package intler_iot.controllers.entities;

import java.util.Objects;

public class OrderData {
    private String name;
    private double value;
    private String login;
    private String password;
    private String deviceName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "OrderData{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", deviceName='" + deviceName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderData orderData = (OrderData) o;
        return Double.compare(orderData.value, value) == 0 &&
                Objects.equals(name, orderData.name) &&
                Objects.equals(login, orderData.login) &&
                Objects.equals(password, orderData.password) &&
                Objects.equals(deviceName, orderData.deviceName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value, login, password, deviceName);
    }
}
