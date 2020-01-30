package intler_iot.services;

import intler_iot.dao.UserDao;
import intler_iot.dao.entities.User;
import intler_iot.services.exceptions.NotAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;

@Service
public class UserService {

    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public User authUser(String login, String password) throws NotAuthException{
        User user;
        try {
            user = userDao.getByLoginPassword(login, password);
        } catch (NoResultException e) {
            throw new NotAuthException("Login and email not match");
        }
        catch (Exception e) {
            throw new NotAuthException(e);
        }
        return user;
    }
}
