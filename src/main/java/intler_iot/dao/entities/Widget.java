package intler_iot.dao.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "widgets")
public class Widget {

    public Widget() {
        super();
    }

    public Widget(Sensor sensor) {
        this.measure = "";
        this.keyWard = sensor.getName();
        this.name = sensor.getName();
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
    private String measure;


    @Column(name = "keyward")
    private String keyWard;

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

    @Override
    public String toString() {
        return "Widget{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color=" + color +
                ", measure='" + measure + '\'' +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Widget widget = (Widget) o;
        return id == widget.id &&
                color == widget.color &&
                Objects.equals(name, widget.name) &&
                Objects.equals(measure, widget.measure) &&
                Objects.equals(user, widget.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, color, measure, user);
    }
}
