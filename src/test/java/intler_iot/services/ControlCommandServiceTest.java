package intler_iot.services;

import intler_iot.dao.ControlCommandDao;
import intler_iot.dao.entities.CommandCondition;
import intler_iot.dao.entities.ControlCommand;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ControlCommandServiceTest {

    @InjectMocks
    ControlCommandService controlCommandService = new ControlCommandService();

    @Mock
    UserService userServiceMock;

    @Mock
    ControlCommandDao controlCommandDao;

    private ControlCommand getControlCommand(String name, int action, double value) {
        ControlCommand controlCommand = new ControlCommand();
        controlCommand.setTargetName(name);
        controlCommand.setAction(action);
        controlCommand.setValue(value);
        controlCommand.setConditions(new ArrayList<>());

        return controlCommand;
    }

    private CommandCondition createCommandCondition(String name, int condition, double value) {
        CommandCondition commandCondition = new CommandCondition();
        commandCondition.setSensorName(name);
        commandCondition.setConditionType(condition);
        commandCondition.setValue(value);

        return commandCondition;
    }

    @Test
    public void getCloudLogicOrders_return1Order() {
        HashMap<String, Double> sensorsValues = new HashMap<>();
        sensorsValues.put("sensor1", 1.0);
        sensorsValues.put("sensor2", 2.0);
        sensorsValues.put("sensor3", 3.0);

        ControlCommand controlCommand = getControlCommand("exec1", ControlCommand.ACTION_TURN_ON, 0);
        controlCommand.getConditions().add(createCommandCondition("sensor1", CommandCondition.CONDITION_LESS, 100));
        when(controlCommandDao.getAll(any())).thenReturn(Arrays.asList(controlCommand));

        HashMap<String,Double> orders = controlCommandService.getCloudLogicOrders(sensorsValues);

        assertEquals(orders.keySet().size(),1);
        assertEquals((double)orders.get("exec1"), 1.0, 0.01);
    }

    @Test
    public void getCloudLogicOrders_return2Order() {
        HashMap<String, Double> sensorsValues = new HashMap<>();
        sensorsValues.put("sensor1", 1.0);
        sensorsValues.put("sensor2", 2.0);
        sensorsValues.put("sensor3", 3.0);

        ControlCommand controlCommand1 = getControlCommand("exec1", ControlCommand.ACTION_TURN_OFF, 0);
        controlCommand1.getConditions().add(createCommandCondition("sensor1", CommandCondition.CONDITION_MORE, 0.5));
        ControlCommand controlCommand2 = getControlCommand("exec2", ControlCommand.ACTION_VALUE, 10);
        controlCommand2.getConditions().add(createCommandCondition("sensor2", CommandCondition.CONDITION_EQUALS, 2.0));

        when(controlCommandDao.getAll(any())).thenReturn(Arrays.asList(controlCommand1, controlCommand2));

        HashMap<String,Double> orders = controlCommandService.getCloudLogicOrders(sensorsValues);

        assertEquals(2,orders.keySet().size());
        assertEquals(0.0, (double)orders.get("exec1"), 0.01);
        assertEquals(10.0, (double)orders.get("exec2"), 0.01);
    }

    @Test
    public void getCloudLogicOrders_return1OrderAnd1ConditionFail() {
        HashMap<String, Double> sensorsValues = new HashMap<>();
        sensorsValues.put("sensor1", 1.0);
        sensorsValues.put("sensor2", 2.1);
        sensorsValues.put("sensor3", 3.0);

        ControlCommand controlCommand1 = getControlCommand("exec1", ControlCommand.ACTION_TURN_OFF, 0);
        controlCommand1.getConditions().add(createCommandCondition("sensor1", CommandCondition.CONDITION_MORE_EQUALS, 0.5));
        ControlCommand controlCommand2 = getControlCommand("exec2", ControlCommand.ACTION_VALUE, 10);
        controlCommand2.getConditions().add(createCommandCondition("sensor2", CommandCondition.CONDITION_EQUALS, 2.0));
        ControlCommand controlCommand3 = getControlCommand("exec3", ControlCommand.ACTION_VALUE, 10);
        controlCommand3.getConditions().add(createCommandCondition("sensor3", CommandCondition.CONDITION_LESS_EQUALS, 0.05));

        when(controlCommandDao.getAll(any())).thenReturn(Arrays.asList(controlCommand1, controlCommand2));

        HashMap<String,Double> orders = controlCommandService.getCloudLogicOrders(sensorsValues);

        assertEquals(1,orders.keySet().size());
        assertEquals((double)orders.get("exec1"), 0.0, 0.01);
    }
}
