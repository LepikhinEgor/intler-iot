package intler_iot.dao.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "command_condition")
public class CommandCondition {

    @Id
    @GenericGenerator(
            name = "command_condition_sequence",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "COMMAND_CONDITION_SEQUENCE"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "command_condition_sequence")
    private long id;

    @Column(name = "sensor_name")
    private String sensorName;

    @Column(name = "type")
    private int conditionType;

    @Column(name = "value")
    private long value;

    @JoinColumn(name = "command_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ControlCommand owner;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public int getConditionType() {
        return conditionType;
    }

    public void setConditionType(int conditionType) {
        this.conditionType = conditionType;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public ControlCommand getOwner() {
        return owner;
    }

    public void setOwner(ControlCommand owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommandCondition that = (CommandCondition) o;
        return id == that.id &&
                conditionType == that.conditionType &&
                value == that.value &&
                Objects.equals(sensorName, that.sensorName) &&
                Objects.equals(owner, that.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sensorName, conditionType, value, owner);
    }

    @Override
    public String toString() {
        return "CommandCondition{" +
                "id=" + id +
                ", sensorName='" + sensorName + '\'' +
                ", conditionType=" + conditionType +
                ", value=" + value +
                '}';
    }
}
