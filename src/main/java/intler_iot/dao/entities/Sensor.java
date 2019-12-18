package intler_iot.dao.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "sensors")
public class Sensor {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private double value;

    @JoinColumn(name = "device_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Device device;

    @CreationTimestamp
    @Column(name = "arrive_time")
    private Timestamp arriveTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Timestamp getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Timestamp arriveTime) {
        this.arriveTime = arriveTime;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sensor sensor = (Sensor) o;
        return id == sensor.id &&
                Double.compare(sensor.value, value) == 0 &&
                Objects.equals(name, sensor.name) &&
                Objects.equals(device, sensor.device) &&
                Objects.equals(arriveTime, sensor.arriveTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, value, device, arriveTime);
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", device=" + device +
                ", arriveTime=" + arriveTime +
                '}';
    }
}
