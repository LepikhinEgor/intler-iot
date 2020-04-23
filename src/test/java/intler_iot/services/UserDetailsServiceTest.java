package intler_iot.services;

import intler_iot.dao.entities.User;
import intler_iot.services.security.UserDetailsFactory;
import intler_iot.services.security.UserDetailsServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceTest {

    @InjectMocks
    UserDetailsServiceImpl userDetailsService = new UserDetailsServiceImpl();

    @Mock
    UserService userServiceMock;

    @Mock
    UserDetailsFactory userDetailsFactory;

    @Test
    public void loadUserByUsername_returnUser() {
        when(userServiceMock.getByLogin(anyString())).thenReturn(new User());

        UserDetails userDetails = userDetailsService.loadUserByUsername("admin");

        verify(userDetailsFactory).createUserDetails(any(User.class));
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
