package intler_iot.controllers.site;

import intler_iot.dao.entities.User;
import intler_iot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @PostMapping("register-user")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        System.out.println(user);
        userService.registerUser(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
