package intler_iot.controllers.site;

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
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        System.out.println(user);
        userService.registerUser(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("registration/check-login-is-free")
    @ResponseBody
    public ResponseEntity<Boolean> checkLoginIsBusy(@RequestParam("login") String login) {
        System.out.println(login);
        boolean isFree = userService.checkLoginIsFree(login);

        return new ResponseEntity<>(isFree, HttpStatus.OK);
    }
}
