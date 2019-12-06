package intler_iot.controllers.entities;

import java.util.HashMap;
import java.util.Objects;

public class DeviceData {
    private HashMap<String, Double> sensorsValue;

    public void setSensorsValue(HashMap<String, Double> sensorsValue) {
        this.sensorsValue = sensorsValue;
    }

    public HashMap<String, Double> getSensorsValue() {
        return sensorsValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceData that = (DeviceData) o;
        return sensorsValue.equals(that.sensorsValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sensorsValue);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
