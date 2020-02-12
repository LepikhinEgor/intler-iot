package intler_iot.dao;

import intler_iot.dao.entities.User;

import javax.persistence.*;
import java.util.List;

public abstract class UserDao {

   public abstract void create(User user);
   public abstract User getByLoginPassword(String login, String password);
   public abstract User getByLogin(String login);
}
