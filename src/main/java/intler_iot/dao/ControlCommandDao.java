package intler_iot.dao;

import intler_iot.dao.entities.ControlCommand;

public abstract class ControlCommandDao {
    public abstract void save(ControlCommand command);
}
