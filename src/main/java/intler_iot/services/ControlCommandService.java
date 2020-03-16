package intler_iot.services;

import intler_iot.dao.ControlCommandDao;
import intler_iot.dao.entities.CommandCondition;
import intler_iot.dao.entities.ControlCommand;
import intler_iot.dao.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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

    public HashMap<String,Double> getCloudLogicOrders(HashMap<String, Double> sensorsValues) {
        List<ControlCommand> controlCommands = getControlCommands();
        HashMap<String,Double> cloudOrders = new HashMap<>();

        for (ControlCommand command : controlCommands) {
            for (CommandCondition condition : command.getConditions()) {
                if (sensorsValues.containsKey(condition.getSensorName())) {
                    if (ckeckCondition(condition, sensorsValues.get(condition.getSensorName()))) {
                        cloudOrders.put(command.getTargetName(), getCommandValue(command));
                    }
                } else {
                    break;
                }
            }
        }

        return cloudOrders;
    }

    private boolean ckeckCondition(CommandCondition condition, Double sensorValue) {
        boolean isMet = false;

        switch (condition.getConditionType()) {
            case CommandCondition.CONDITION_MORE: isMet = sensorValue > condition.getValue(); break;
            case CommandCondition.CONDITION_LESS: isMet = sensorValue < condition.getValue(); break;
            case CommandCondition.CONDITION_MORE_EQUALS: isMet = sensorValue >= condition.getValue(); break;
            case CommandCondition.CONDITION_LESS_EQUALS: isMet = sensorValue <= condition.getValue(); break;
            case CommandCondition.CONDITION_EQUALS: isMet = sensorValue == condition.getValue(); break;
        }

        return isMet;
    }


    private double getCommandValue(ControlCommand command) {
        double value;

        switch (command.getAction()) {
            case ControlCommand.ACTION_TURN_ON: value = 1; break;
            case ControlCommand.ACTION_TURN_OFF: value = 0; break;
            case ControlCommand.ACTION_VALUE: value = command.getValue(); break;
            default: throw new RuntimeException("No control command action define");
        }

        return value;
    }
}
