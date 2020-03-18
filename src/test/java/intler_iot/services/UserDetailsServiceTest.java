package intler_iot.services;

import intler_iot.dao.entities.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class UserDetailsServiceTest {

    UserDetailsServiceImpl userDetailsService;

    @Mock
    UserService userServiceMock;

    @Before
    public void initTest() {
        userDetailsService = new UserDetailsServiceImpl();
        MockitoAnnotations.initMocks(this);
        inject();
    }

    private void inject() {
        userDetailsService.setUserService(userServiceMock);
    }

    @Test
    public void loadUserByUsername_returnUser() {
        when(userServiceMock.getByLogin(any())).thenReturn(new User());

        UserDetails userDetails = userDetailsService.loadUserByUsername("admin");

        assertTrue(userDetails instanceof User);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_throwUsernameNotFoundExceptionByNullFoundedUser() {
        when(userServiceMock.getByLogin(any())).thenReturn(null);

        UserDetails userDetails = userDetailsService.loadUserByUsername("admin");
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_throwUsernameNotFoundExceptionByUserServiceException() {
        when(userServiceMock.getByLogin(any())).thenThrow(new RuntimeException());

        UserDetails userDetails = userDetailsService.loadUserByUsername("admin");
    }
}
