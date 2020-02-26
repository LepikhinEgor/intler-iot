package intler_iot.controllers.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogicController {

    @GetMapping("/console/logic")
    public String getLogicPage() {
        return "logic";
    }
}
