package intler_iot.dao.entities;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "devices")
@IdClass(Device.DeviceKey.class)
public class Device {

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @UpdateTimestamp
    @Column(name = "last_connection")
    private Timestamp lastDeviceMessageTime;

    @Id
    @JoinColumn(name = "owner_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

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

    public DeviceKey getKey() {
        return new DeviceKey(name, owner);
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

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public static class DeviceKey implements Serializable {
        static final long serialVersionUID = 1L;

        private String name;
        private User owner;

        public DeviceKey() {
            super();
        }

        public DeviceKey(String name, User user) {
            this.name = name;
            this.owner = user;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public User getOwner() {
            return owner;
        }

        public void setOwner(User owner) {
            this.owner = owner;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DeviceKey deviceKey = (DeviceKey) o;
            return Objects.equals(name, deviceKey.name) &&
                    Objects.equals(owner, deviceKey.owner);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, owner);
        }
    }
}
