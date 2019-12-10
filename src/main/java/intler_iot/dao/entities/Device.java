package intler_iot.dao.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "devices")
public class Device {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "last_connection")
    private Timestamp lastDeviceMessageTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

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
}
