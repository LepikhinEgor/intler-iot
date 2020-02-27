package intler_iot.controllers.site;

import intler_iot.dao.entities.ControlCommand;
import intler_iot.services.ControlCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LogicController {

    private ControlCommandService controlCommandService;

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
}
