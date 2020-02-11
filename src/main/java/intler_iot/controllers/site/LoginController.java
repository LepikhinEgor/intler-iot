package intler_iot.controllers.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("registration")
    public String getRegistrationPage() {
        return "registration";
    }
}
