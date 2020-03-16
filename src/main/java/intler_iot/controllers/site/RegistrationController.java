package intler_iot.controllers.site;

import intler_iot.controllers.entities.RegistrationDTO;
import intler_iot.dao.entities.User;
import intler_iot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("registration")
    public String getRegistrationPage() {
        return "registration";
    }

    @PostMapping("registration/register-user")
    @ResponseBody
    public RegistrationDTO registerUser(@RequestBody User user) {
        RegistrationDTO message = userService.registerUser(user);

        return message;
    }

    @GetMapping("registration/check-login-is-free")
    @ResponseBody
    public String checkLoginIsBusy(@RequestParam("login") String login) {
        boolean isFree = userService.checkLoginIsFree(login);

        return Boolean.toString(isFree);
    }

    @GetMapping("registration/check-email-is-free")
    @ResponseBody
    public String checkEmailIsBusy(@RequestParam("email") String email) {
        boolean isFree = userService.checkEmailIsFree(email);

        return Boolean.toString(isFree);
    }
}
