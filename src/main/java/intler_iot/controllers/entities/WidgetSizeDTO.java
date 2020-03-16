package intler_iot.controllers.entities;

import java.util.Objects;

public class WidgetSizeDTO {
    private long id;
    private int width;
    private int height;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WidgetSizeDTO that = (WidgetSizeDTO) o;
        return id == that.id &&
                width == that.width &&
                height == that.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, width, height);
    }

    @Override
    public String toString() {
        return "WidgetSize{" +
                "id=" + id +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
