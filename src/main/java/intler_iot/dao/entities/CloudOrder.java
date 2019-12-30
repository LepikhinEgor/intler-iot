package intler_iot.dao.entities;

import intler_iot.controllers.entities.OrderData;
import org.hibernate.criterion.Order;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "cloud_orders")
public class CloudOrder {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private double value;

    @Column(name = "timing")
    private Timestamp timing;

    @Column(name = "used")
    private boolean used;

    @JoinColumn(name = "device_id")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Device device;

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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Timestamp getTiming() {
        return timing;
    }

    public void setTiming(Timestamp timing) {
        this.timing = timing;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    @Override
    public String toString() {
        return "CloudOrder{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", timing=" + timing +
                ", used=" + used +
                ", device=" + device +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CloudOrder that = (CloudOrder) o;
        return id == that.id &&
                Double.compare(that.value, value) == 0 &&
                used == that.used &&
                Objects.equals(name, that.name) &&
                Objects.equals(timing, that.timing) &&
                Objects.equals(device, that.device);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, value, timing, used, device);
    }
}
