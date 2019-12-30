package intler_iot.controllers.entities;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SensorsData {

    private String login;
    private String password;
    private String deviceName;
    private String deviceType;
    private List<String> ordersAccepted;

    private HashMap<String, Double> sensorsValue;

    public void setSensorsValue(HashMap<String, Double> sensorsValue) {
        this.sensorsValue = sensorsValue;
    }

    public HashMap<String, Double> getSensorsValue() {
        return sensorsValue;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public List<String> getOrdersAccepted() {
        return ordersAccepted;
    }

    public void setOrdersAccepted(List<String> ordersAccepted) {
        this.ordersAccepted = ordersAccepted;
    }

    @Override
    public String toString() {
        return "SensorsData{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", ordersAccepted=" + ordersAccepted +
                ", sensorsValue=" + sensorsValue +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SensorsData that = (SensorsData) o;
        return Objects.equals(login, that.login) &&
                Objects.equals(password, that.password) &&
                Objects.equals(deviceName, that.deviceName) &&
                Objects.equals(deviceType, that.deviceType) &&
                Objects.equals(ordersAccepted, that.ordersAccepted) &&
                Objects.equals(sensorsValue, that.sensorsValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, deviceName, deviceType, ordersAccepted, sensorsValue);
    }
}
