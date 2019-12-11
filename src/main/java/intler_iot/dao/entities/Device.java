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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public class DeviceKey implements Serializable {
        private String name;
        private User owner;

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
