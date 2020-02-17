package intler_iot.dao.hibernate;

import intler_iot.dao.UserDao;
import intler_iot.dao.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoHibernate extends UserDao{

    private static final Logger logger = LoggerFactory.getLogger(UserDaoHibernate.class);

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(User user) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.save(user);

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public User getByLoginPassword(String login, String password) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        try {
            Query query = session.createQuery("from User where login = :loginParam and password = :password");
            query.setParameter("loginParam", login);
            query.setParameter("password", password);

            User foundUser = (User)query.getSingleResult();

            transaction.commit();

            return foundUser;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public User getByLogin(String login) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        try {
            Query query = session.createQuery("from User where login = :login");
            query.setParameter("login", login);

            User user = (User)query.uniqueResult();

            tx.commit();

            return user;
        } catch (RuntimeException e) {
            tx.rollback();
            logger.error(e.getMessage(),e);
            throw e;
        }
    }

    @Override
    public User getByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        try {
            Query query = session.createQuery("from User where email = :email");
            query.setParameter("email", email);

            User user = (User)query.uniqueResult();

            tx.commit();

            return user;
        } catch (RuntimeException e) {
            tx.rollback();
            logger.error(e.getMessage(),e);
            throw e;
        }
    }
}
