package intler_iot.dao.hibernate;

import intler_iot.dao.SensorDao;
import intler_iot.dao.entities.Sensor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SensorDaoHibernate extends SensorDao {

    SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Sensor sensor) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.save(sensor);

        transaction.commit();
        session.close();
    }

    @Override
    public void recordAll(List<Sensor> sensors) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        for (Sensor sensor : sensors)
            session.save(sensor);

        transaction.commit();
        session.close();
    }
}
