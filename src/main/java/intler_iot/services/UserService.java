package intler_iot.services;

import intler_iot.dao.UserDao;
import intler_iot.dao.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getUserByLogin(String login) {
        User user = userDao.getByLogin(login);

        return user;
    }
}
