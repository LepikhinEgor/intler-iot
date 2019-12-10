package intler_iot.dao;

import intler_iot.dao.entities.User;

import javax.persistence.*;

public abstract class UserDao {

   public abstract void create(User user);
}
