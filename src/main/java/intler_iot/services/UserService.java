package intler_iot.services;

import intler_iot.controllers.entities.RegistrationMessage;
import intler_iot.dao.UserDao;
import intler_iot.dao.entities.User;
import intler_iot.services.exceptions.NotAuthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.List;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private UserDao userDao;

    private PasswordEncoder passwordEncoder;

    private WebSecurityConfigurerAdapter webSecurityConfigurerAdapter;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setWebSecurityConfigurerAdapter(WebSecurityConfigurerAdapter webSecurityConfigurerAdapter) {
        this.webSecurityConfigurerAdapter = webSecurityConfigurerAdapter;
    }

    public User getByLogin(String login) {
        User user = userDao.getByLogin(login);

        return user;
    }

    public User authUser(String login, String password) throws NotAuthException{
        try {
            User user;

            if (isAuthenticated()) {
                user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            }
            else {
                Authentication auth = new UsernamePasswordAuthenticationToken(login,password);
                Authentication authUser = webSecurityConfigurerAdapter.authenticationManagerBean().authenticate(auth);
                SecurityContextHolder.getContext().setAuthentication(authUser);

                user = (User)authUser.getPrincipal();
            }

            return user;
        } catch (NoResultException e) {
            throw new NotAuthException("Login not found");
        }
        catch (Exception e) {
            throw new NotAuthException(e);
        }
    }

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication != null
                && !(authentication instanceof AnonymousAuthenticationToken)
                && authentication.isAuthenticated();
    }

    public RegistrationMessage registerUser(User user) {
        int validStatus = checkUserDataIsValid(user);
        if (validStatus != RegistrationMessage.SUCCESS)
            return new RegistrationMessage(validStatus);
        if (!checkLoginIsFree(user.getLogin()))
            return new RegistrationMessage(RegistrationMessage.LOGIN_BUSY);
        if (!checkEmailIsFree(user.getEmail()))
            return new RegistrationMessage(RegistrationMessage.EMAIL_BUSY);

        user.setPassword(encodePassword(user.getPassword()));

        try {
            userDao.create(user);
        } catch (Throwable th) {
            logger.error(th.getMessage(), th);
            return new RegistrationMessage(RegistrationMessage.FAIL);
        }

        return new RegistrationMessage(RegistrationMessage.SUCCESS);
    }

    public boolean checkLoginIsFree(String login) {
        User user = userDao.getByLogin(login);

        return user == null;
    }

    public boolean checkEmailIsFree(String email) {
        User user = userDao.getByEmail(email);

        return user == null;
    }

    private String encodePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.encode(password);
    }

    private int checkUserDataIsValid(User user) {
        if (!checkLoginIsValid(user.getLogin()))
            return RegistrationMessage.LOGIN_INVALID;

        if (!checkEmailIsValid(user.getEmail()))
            return RegistrationMessage.EMAIL_INVALID;

        if (!checkPasswordIsValid(user.getPassword()))
            return RegistrationMessage.PASSWORD_INVALID;

        return RegistrationMessage.SUCCESS;
    }

    private boolean checkLoginIsValid(String login) {
        return login.matches("^(?!.*\\.\\.)(?!\\.)(?!.*\\.$)(?!\\d+$)[a-zA-Z0-9_.]{5,50}$");
    }

    private boolean checkEmailIsValid(String email) {
        return email.matches("^([A-Za-z0-9_\\-.])+@([A-Za-z0-9_\\-.])+\\.([A-Za-z]{2,4})$");
    }

    private boolean checkPasswordIsValid(String password) {
        return password.matches("[0-9a-zA-Z!@#$%^&*]{6,}");
    }
}
