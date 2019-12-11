package intler_iot.dao.hibernate;

import intler_iot.dao.UserDao;
import intler_iot.dao.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoHibernate extends UserDao{

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.save(user);

        transaction.commit();
        session.close();
    }

    @Override
    public User getByLogin(String login) {
        System.out.println(login + " received");
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from User where login = :loginParam");
        query.setParameter("loginParam", login);
//        Query query = session.createQuery("from User");
//        User foundUser = (User)query.list().get(0);

//        for (Object user: query.list()) {
//            System.out.println(user.toString());
//        }

        transaction.commit();
        session.close();

//        return foundUser;
        return null;
    }
}
