package intler_iot.services;

import intler_iot.controllers.entities.RegistrationDTO;
import intler_iot.dao.UserDao;
import intler_iot.dao.entities.User;
import intler_iot.services.exceptions.NotAuthException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    UserDao userDaoMock;

    @Mock
    PasswordEncoder passwordEncoderMock;

    UserService userService;

    @Before
    public void initTest() {
        userService = new UserService();
        MockitoAnnotations.initMocks(this);
        inject();
    }

    private void inject() {
        userService.setUserDao(userDaoMock);
        userService.setPasswordEncoder(passwordEncoderMock);
    }

    @Test
    public void registerUser_successRecord() {
        User user = new User();
        user.setLogin("admin");
        user.setPassword("qwerty");
        user.setEmail("login@mail.ru");

        doNothing().when(userDaoMock).create(any());

        RegistrationDTO registrationStatus = userService.registerUser(user);

        assertEquals(registrationStatus.getStatus(), RegistrationDTO.SUCCESS);
    }

    @Test
    public void registerUser_invalidLogin() {
        User user = new User();
        user.setLogin("admin.");
        user.setPassword("qwerty");
        user.setEmail("login@mail.ru");

        doNothing().when(userDaoMock).create(any());

        RegistrationDTO registrationStatus = userService.registerUser(user);

        assertEquals(registrationStatus.getStatus(), RegistrationDTO.LOGIN_INVALID);
    }

    @Test
    public void registerUser_invalidEmail() {
        User user = new User();
        user.setLogin("admin");
        user.setPassword("qwerty");
        user.setEmail("login.mail.ru");

        doNothing().when(userDaoMock).create(any());

        RegistrationDTO registrationStatus = userService.registerUser(user);

        assertEquals(registrationStatus.getStatus(), RegistrationDTO.EMAIL_INVALID);
    }

    @Test
    public void registerUser_invalidPassword() {
        User user = new User();
        user.setLogin("admin");
        user.setPassword("1111");
        user.setEmail("login@mail.ru");

        doNothing().when(userDaoMock).create(any());

        RegistrationDTO registrationStatus = userService.registerUser(user);

        assertEquals(registrationStatus.getStatus(), RegistrationDTO.PASSWORD_INVALID);
    }

    @Test
    public void registerUser_loginBusy() {
        User user = new User();
        user.setLogin("admin");
        user.setPassword("qwerty");
        user.setEmail("login@mail.ru");

        doNothing().when(userDaoMock).create(any());
        when(userDaoMock.getByLogin(any())).thenReturn(new User());

        RegistrationDTO registrationStatus = userService.registerUser(user);

        assertEquals(registrationStatus.getStatus(), RegistrationDTO.LOGIN_BUSY);
    }

    @Test
    public void registerUser_emailBusy() {
        User user = new User();
        user.setLogin("admin");
        user.setPassword("qwerty");
        user.setEmail("login@mail.ru");

        doNothing().when(userDaoMock).create(any());
        when(userDaoMock.getByEmail(any())).thenReturn(new User());

        RegistrationDTO registrationStatus = userService.registerUser(user);

        assertEquals(registrationStatus.getStatus(), RegistrationDTO.EMAIL_BUSY);
    }


}
