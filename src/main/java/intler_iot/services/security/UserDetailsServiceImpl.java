package intler_iot.services.security;

import intler_iot.dao.entities.User;
import intler_iot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserService userService;

    private UserDetailsFactory userDetailsFactory;

    @Autowired
    public void setUserDetailsFactory(UserDetailsFactory userDetailsFactory) {
        this.userDetailsFactory = userDetailsFactory;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        final User foundUser;
        UserDetails userDetails;

        try {
            foundUser = userService.getByLogin(login);

            if (foundUser == null)
                throw new UsernameNotFoundException("User with login " + login + " not found");

            userDetails = userDetailsFactory.createUserDetails(foundUser);
            System.out.println(userDetails.getAuthorities());
        } catch (Throwable e ) {
            throw new UsernameNotFoundException(e.getMessage(), e);
        }

        return userDetails;
    }
}
