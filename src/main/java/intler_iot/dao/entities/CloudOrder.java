package intler_iot.dao.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "cloud_orders")
public class CloudOrder {

    @Id
    @GenericGenerator(
            name = "cloud_order_sequence",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "CLOUD_ORDER_SEQUENCE"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cloud_order_sequence")
    private long id;

    @Column(name = "keyWard")
    private String keyWard;

    @Column(name = "value")
    private double value;

    @Column(name = "timing")
    private Timestamp timing;

    @Column(name = "used")
    private boolean used;

    @Column(name = "removed")
    private boolean removed;

    @JoinColumn(name = "device_id")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Device device;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKeyWard() {
        return keyWard;
    }

    public void setKeyWard(String keyWard) {
        this.keyWard = keyWard;
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

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CloudOrder that = (CloudOrder) o;
        return id == that.id &&
                Double.compare(that.value, value) == 0 &&
                used == that.used &&
                removed == that.removed &&
                Objects.equals(keyWard, that.keyWard) &&
                Objects.equals(timing, that.timing) &&
                Objects.equals(device, that.device);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, keyWard, value, timing, used, removed, device);
    }

    @Override
    public String toString() {
        return "CloudOrder{" +
                "id=" + id +
                ", keyWard='" + keyWard + '\'' +
                ", value=" + value +
                ", timing=" + timing +
                ", used=" + used +
                ", removed=" + removed +
                ", device=" + device +
                '}';
    }

}
