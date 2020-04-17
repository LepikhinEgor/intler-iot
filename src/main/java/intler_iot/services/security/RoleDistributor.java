package intler_iot.services.security;

import intler_iot.config.AppProperties;
import intler_iot.dao.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class RoleDistributor {

    AppProperties appProperties;

    @Autowired
    public void setAppProperties(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public Collection<GrantedAuthority> getUserRoles(User user) {
       List<GrantedAuthority> roles = new ArrayList<>();

       if (user == null)
           return Collections.emptyList();

       roles.add(new SimpleGrantedAuthority("ROLE_USER"));

       if (appProperties.getAdminName().equals(user.getLogin()))
           roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

       return roles;
   }
}
