package intler_iot.controllers.site;

import intler_iot.controllers.entities.RegistrationMessage;
import intler_iot.dao.entities.User;
import intler_iot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public RegistrationMessage registerUser(@RequestBody User user) {
        RegistrationMessage message = userService.registerUser(user);

        return message;
    }

    @GetMapping("registration/check-login-is-free")
    @ResponseBody
    public String checkLoginIsBusy(@RequestParam("login") String login) {
        System.out.println(login);
        boolean isFree = userService.checkLoginIsFree(login);

        return Boolean.toString(isFree);
    }
}
