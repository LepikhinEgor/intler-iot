package intler_iot.dao.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "control_commands")
public class ControlCommand {

    public static final int ACTION_TURN_ON = 1;
    public static final int ACTION_TURN_OFF = 2;
    public static final int ACTION_VALUE = 3;

    @Id
    @GenericGenerator(
            name = "control_command_sequence",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "CONTROL_COMMAND_SEQUENCE"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "control_command_sequence")
    private long id;

    @Column(name = "target")
    private String targetName;

    @Column(name = "action")
    private int action;

    @Column(name = "value")
    private double value;

    @OneToMany(mappedBy = "command", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<CommandCondition> conditions;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public List<CommandCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<CommandCondition> conditions) {
        this.conditions = conditions;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ControlCommand that = (ControlCommand) o;
        return id == that.id &&
                action == that.action &&
                value == that.value &&
                Objects.equals(targetName, that.targetName) &&
                Objects.equals(conditions, that.conditions) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, targetName, action, value, conditions, user);
    }

    @Override
    public String toString() {
        return "ControlCommand{" +
                "id=" + id +
                ", targetName='" + targetName + '\'' +
                ", action=" + action +
                ", value=" + value +
                ", conditions=" + conditions +
                ", user=" + user +
                '}';
    }
}
