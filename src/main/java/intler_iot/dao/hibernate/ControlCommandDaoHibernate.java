package intler_iot.dao.hibernate;

import intler_iot.dao.ControlCommandDao;
import intler_iot.dao.entities.CommandCondition;
import intler_iot.dao.entities.ControlCommand;
import intler_iot.dao.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ControlCommandDaoHibernate extends ControlCommandDao {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<ControlCommand> getAll(User user) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("from ControlCommand where user = :user");
        query.setParameter("user", user);

        List<ControlCommand> commands = query.list();

        return commands;
    }

    @Override
    public void save(ControlCommand command) {
        Session session = sessionFactory.getCurrentSession();

        session.saveOrUpdate(command);
    }
}
