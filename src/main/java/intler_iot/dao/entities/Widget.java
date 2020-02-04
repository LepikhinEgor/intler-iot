package intler_iot.dao.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "widgets")
public class Widget {

    public Widget() {
        super();
    }

    public Widget(Sensor sensor) {
        this.name = sensor.getName();
        this.sensor = sensor;
        this.user = sensor.getDevice().getOwner();
    }

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "color")
    private int color;

    @JsonIgnore
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "sensor_id")
    @OneToOne(fetch = FetchType.LAZY)
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

    @Override
    public String toString() {
        return "Widget{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color=" + color +
                ", user=" + user +
                ", sensor=" + sensor +
                '}';
    }
}
