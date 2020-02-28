package intler_iot.dao.hibernate;

import intler_iot.dao.ControlCommandDao;
import intler_iot.dao.entities.CommandCondition;
import intler_iot.dao.entities.ControlCommand;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ControlCommandDaoHibernate extends ControlCommandDao {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(ControlCommand command) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        try {
            session.save(command);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }
}
