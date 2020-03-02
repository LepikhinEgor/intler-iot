package intler_iot.dao;

import intler_iot.dao.entities.ControlCommand;
import intler_iot.dao.entities.User;

import java.util.List;

public abstract class ControlCommandDao {
    public abstract List<ControlCommand> getAll(User user);
    public abstract void save(ControlCommand command);
}
