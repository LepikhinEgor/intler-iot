package intler_iot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WidgetController {

    @GetMapping(value = {"/**", "/dashboard"})
    public String getDashboardPage() {
        System.out.println("GET");
        return "dashboard";
    }
}
