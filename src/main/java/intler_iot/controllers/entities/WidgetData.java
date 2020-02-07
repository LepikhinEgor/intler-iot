package intler_iot.controllers.entities;

import intler_iot.dao.entities.Sensor;
import intler_iot.dao.entities.Widget;

import java.util.Objects;

public class WidgetData {
    private Widget widget;
    private Sensor sensor;

    public WidgetData(Widget widget, Sensor sensor) {
        this.widget = widget;
        this.sensor = sensor;
    }

    public Widget getWidget() {
        return widget;
    }

    public void setWidget(Widget widget) {
        this.widget = widget;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WidgetData that = (WidgetData) o;
        return Objects.equals(widget, that.widget) &&
                Objects.equals(sensor, that.sensor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(widget, sensor);
    }

    @Override
    public String toString() {
        return "WidgetData{" +
                "widget=" + widget +
                ", sensor=" + sensor +
                '}';
    }
}
