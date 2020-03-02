package intler_iot.services;

import intler_iot.dao.ControlCommandDao;
import intler_iot.dao.entities.ControlCommand;
import intler_iot.dao.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ControlCommandService {

    private ControlCommandDao controlCommandDao;

    @Autowired
    public void setControlCommandDao(ControlCommandDao controlCommandDao) {
        this.controlCommandDao = controlCommandDao;
    }

    public void saveControlCommand(ControlCommand command) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        command.getConditions().forEach(condition -> condition.setCommand(command));
        command.setUser(user);

        controlCommandDao.save(command);
    }

    public List<ControlCommand> getControlCommands() {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<ControlCommand>  commands = controlCommandDao.getAll(user);

        return commands;
    }
}
