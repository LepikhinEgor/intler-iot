package intler_iot.dao.entities;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "devices")
public class Device {

    @Id
    @GenericGenerator(
            name = "device_sequence",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "DEVICE_SEQUENCE"),
                    @Parameter(name = "initial_value", value = "3"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "device_sequence")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "last_connection")
    private Timestamp lastDeviceMessageTime;

    @JoinColumn(name = "owner_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    @OneToMany(mappedBy = "device", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SensorValue> sensorValues;

    @OneToMany(mappedBy = "device", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CloudOrder> orders;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getLastDeviceMessageTime() {
        return lastDeviceMessageTime;
    }

    public void setLastDeviceMessageTime(Timestamp lastDeviceMessageTime) {
        this.lastDeviceMessageTime = lastDeviceMessageTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return Objects.equals(name, device.name) &&
                Objects.equals(type, device.type) &&
                Objects.equals(lastDeviceMessageTime, device.lastDeviceMessageTime) &&
                Objects.equals(owner, device.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, lastDeviceMessageTime, owner);
    }

    @Override
    public String toString() {
        return "Device{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", lastDeviceMessageTime=" + lastDeviceMessageTime +
                ", owner=" + owner +
                '}';
    }

    public User getOwner() {
        return owner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<SensorValue> getSensorValues() {
        return sensorValues;
    }

    public void setSensorValues(List<SensorValue> sensorValues) {
        this.sensorValues = sensorValues;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
