package intler_iot.controllers.entities;

import intler_iot.dao.entities.SensorValue;
import intler_iot.dao.entities.Widget;
import org.codehaus.jackson.annotate.JsonProperty;

import java.sql.Timestamp;
import java.util.Objects;

public class WidgetDTO {
    private long id;
    private String measure;
    @JsonProperty(value = "keyWard")
    private String keyword;
    private int width;
    private int height;
    private int type;
    private String deviceName;
    private int minValue;
    private int maxValue;
    private int icon;
    private int color;
    private double lastValue;

    private boolean hasValue;
    private double value;
    private Timestamp updateTime;

    public WidgetDTO(Widget widget, SensorValue sensorValue) {
        this.id = widget.getId();
        this.measure = widget.getMeasure();
        this.keyword = widget.getKeyWard();
        this.width = widget.getWidth();
        this.height = widget.getHeight();
        this.type = widget.getType();
        this.icon = widget.getIcon();
        this.color = widget.getColor();
        this.deviceName = widget.getDeviceName();
        this.minValue = widget.getMinValue();
        this.maxValue = widget.getMaxValue();
        this.lastValue = widget.getLastValue();

        if (sensorValue != null) {
            this.hasValue = true;
            this.value = sensorValue.getValue();
            this.updateTime = sensorValue.getArriveTime();
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public boolean isHasValue() {
        return hasValue;
    }

    public void setHasValue(boolean hasValue) {
        this.hasValue = hasValue;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public double getLastValue() {
        return lastValue;
    }

    public void setLastValue(double lastValue) {
        this.lastValue = lastValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WidgetDTO widgetDTO = (WidgetDTO) o;
        return id == widgetDTO.id &&
                width == widgetDTO.width &&
                height == widgetDTO.height &&
                type == widgetDTO.type &&
                minValue == widgetDTO.minValue &&
                maxValue == widgetDTO.maxValue &&
                icon == widgetDTO.icon &&
                color == widgetDTO.color &&
                lastValue == widgetDTO.lastValue &&
                hasValue == widgetDTO.hasValue &&
                Double.compare(widgetDTO.value, value) == 0 &&
                Objects.equals(measure, widgetDTO.measure) &&
                Objects.equals(keyword, widgetDTO.keyword) &&
                Objects.equals(deviceName, widgetDTO.deviceName) &&
                Objects.equals(updateTime, widgetDTO.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, measure, keyword, width, height, type, deviceName, minValue, maxValue, icon, color, lastValue, hasValue, value, updateTime);
    }

    @Override
    public String toString() {
        return "WidgetDTO{" +
                "id=" + id +
                ", measure='" + measure + '\'' +
                ", keyword='" + keyword + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", type=" + type +
                ", deviceName='" + deviceName + '\'' +
                ", minValue=" + minValue +
                ", maxValue=" + maxValue +
                ", icon=" + icon +
                ", color=" + color +
                ", lastValue=" + lastValue +
                ", hasValue=" + hasValue +
                ", value=" + value +
                ", updateTime=" + updateTime +
                '}';
    }
}
