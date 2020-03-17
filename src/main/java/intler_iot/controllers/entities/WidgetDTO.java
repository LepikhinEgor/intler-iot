package intler_iot.controllers.entities;

import intler_iot.dao.entities.SensorValue;
import intler_iot.dao.entities.Widget;

import java.util.Objects;

public class WidgetDTO {
    private Widget widget;
    private SensorValue sensorValue;

    public WidgetDTO(Widget widget, SensorValue sensorValue) {
        this.widget = widget;
        this.sensorValue = sensorValue;
    }

    public Widget getWidget() {
        return widget;
    }

    public void setWidget(Widget widget) {
        this.widget = widget;
    }

    public SensorValue getSensorValue() {
        return sensorValue;
    }

    public void setSensorValue(SensorValue sensorValue) {
        this.sensorValue = sensorValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WidgetDTO that = (WidgetDTO) o;
        return Objects.equals(widget, that.widget) &&
                Objects.equals(sensorValue, that.sensorValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(widget, sensorValue);
    }

    @Override
    public String toString() {
        return "WidgetData{" +
                "widget=" + widget +
                ", sensor=" + sensorValue +
                '}';
    }
}
