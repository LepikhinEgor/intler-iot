package intler_iot.controllers.site;

import intler_iot.dao.entities.ControlCommand;
import intler_iot.dao.entities.Sensor;
import intler_iot.services.ControlCommandService;
import intler_iot.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Controller
public class LogicController {

    private ControlCommandService controlCommandService;
    private SensorService sensorService;

    @Autowired
    public void setSensorService(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Autowired
    public void setControlCommandService(ControlCommandService controlCommandService) {
        this.controlCommandService = controlCommandService;
    }

    @GetMapping("/console/logic")
    public String getLogicPage() {
        return "logic";
    }

    @PostMapping("/console/logic/create-control-command")
    @ResponseBody
    public void createControlCommand(@RequestBody ControlCommand command) {
        controlCommandService.saveControlCommand(command);
    }

    @GetMapping("/console/logic/get-sensors-name")
    @ResponseBody
    public HashMap<String, Collection<String>> getUserSensorsName() {
        HashMap<String, Collection<String>> userSensors = sensorService.getSensorsName();

        return userSensors;
    }
}
