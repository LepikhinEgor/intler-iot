package intler_iot.dao.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "widgets")
public class Widget {

    private static final int DEFAULT_WIDTH = 200;
    private static final int DEFAULT_HEIGHT = 200;

    private static final int TYPE_TEXT = 0;
    private static final int TYPE_BUTTON = 1;

    public Widget() {
        super();
    }

    public Widget(Sensor sensor) {
        this.measure = "";
        this.keyWard = sensor.getName();
        this.name = sensor.getName();
        this.user = sensor.getDevice().getOwner();
        this.width = 200;
        this.height = 200;
        this.type = 0;
    }

    @Id
    @GenericGenerator(
            name = "widget_sequence",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "WIDGET_SEQUENCE"),
                    @Parameter(name = "initial_value", value = "4"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "widget_sequence")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "color")
    private int color;

    @Column(name = "measure")
    private String measure;


    @Column(name = "keyward")
    private String keyWard;

    @Column(name = "icon")
    private int icon;

    @Column(name = "width")
    private int width;

    @Column(name = "height")
    private int height;

    @Column(name = "type")
    private int type;

    @JsonIgnore
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getKeyWard() {
        return keyWard;
    }

    public void setKeyWard(String keyWard) {
        this.keyWard = keyWard;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Widget widget = (Widget) o;
        return id == widget.id &&
                color == widget.color &&
                icon == widget.icon &&
                width == widget.width &&
                height == widget.height &&
                type == widget.type &&
                Objects.equals(name, widget.name) &&
                Objects.equals(measure, widget.measure) &&
                Objects.equals(keyWard, widget.keyWard) &&
                Objects.equals(user, widget.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, color, measure, keyWard, icon, width, height, type, user);
    }

    @Override
    public String toString() {
        return "Widget{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color=" + color +
                ", measure='" + measure + '\'' +
                ", keyWard='" + keyWard + '\'' +
                ", icon=" + icon +
                ", width=" + width +
                ", height=" + height +
                ", type=" + type +
                ", user=" + user +
                '}';
    }

}
