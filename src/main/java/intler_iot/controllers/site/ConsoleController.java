package intler_iot.controllers.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConsoleController {

    @GetMapping("/console")
    public String getConsolePage() {
        return "console";
    }
}
