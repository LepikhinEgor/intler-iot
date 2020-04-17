package intler_iot.services.security;

import intler_iot.dao.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsFactory {

    private RoleDistributor roleDistributor;

    @Autowired
    public void setRoleDistributor(RoleDistributor roleDistributor) {
        this.roleDistributor = roleDistributor;
    }

    public UserDetails createUserDetails(User user) {
        UserDetailsAdapter userDetails = new UserDetailsAdapter(user);
        userDetails.setRoleDistributor(roleDistributor);

        return  userDetails;
    }
}
