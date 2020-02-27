package intler_iot.controllers.site;

import intler_iot.dao.entities.ControlCommand;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LogicController {

    @GetMapping("/console/logic")
    public String getLogicPage() {
        return "logic";
    }

    @PostMapping("/console/logic/create-control-command")
    @ResponseBody
    public String createControlCommand(@RequestBody ControlCommand command) {
        System.out.println(command);

        return command.toString();
    }
}
