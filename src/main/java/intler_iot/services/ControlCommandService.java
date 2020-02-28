package intler_iot.services;

import intler_iot.dao.ControlCommandDao;
import intler_iot.dao.entities.ControlCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ControlCommandService {

    private ControlCommandDao controlCommandDao;

    @Autowired
    public void setControlCommandDao(ControlCommandDao controlCommandDao) {
        this.controlCommandDao = controlCommandDao;
    }

    public void saveControlCommand(ControlCommand command) {
        command.getConditions().forEach(condition -> condition.setCommand(command));

        controlCommandDao.save(command);
    }
}
