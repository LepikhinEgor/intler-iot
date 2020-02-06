package intler_iot.dao.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "widgets")
public class Widget {

    public Widget() {
        super();
    }

    public Widget(Sensor sensor) {
        this.measure = 2;
        this.name = sensor.getName();
        this.sensor = sensor;
        this.user = sensor.getDevice().getOwner();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "color")
    private int color;

    @Column(name = "measure")
    private int measure;

    @JsonIgnore
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "sensor_id")
    @OneToOne
    private Sensor sensor;

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

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getMeasure() {
        return measure;
    }

    public void setMeasure(int measure) {
        this.measure = measure;
    }

    @Override
    public String toString() {
        return "Widget{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color=" + color +
                ", measure=" + measure +
                ", user=" + user +
                ", sensor=" + sensor +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Widget widget = (Widget) o;
        return id == widget.id &&
                color == widget.color &&
                measure == widget.measure &&
                Objects.equals(name, widget.name) &&
                Objects.equals(user, widget.user) &&
                Objects.equals(sensor, widget.sensor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, color, measure, user, sensor);
    }
}
