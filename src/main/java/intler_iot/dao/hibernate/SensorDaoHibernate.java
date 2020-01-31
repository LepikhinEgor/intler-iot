package intler_iot.dao.hibernate;

import intler_iot.dao.SensorDao;
import intler_iot.dao.entities.Device;
import intler_iot.dao.entities.Sensor;
import intler_iot.dao.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class SensorDaoHibernate extends SensorDao {

    SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Sensor sensor) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        session.save(sensor);

        transaction.commit();
    }

    @Override
    public void recordAll(List<Sensor> sensors) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        for (Sensor sensor : sensors)
            session.save(sensor);

        transaction.commit();
    }

    @Override
    public void removeOldValues(Timestamp deadline) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("delete from Sensor where arriveTime < :deadline");
        query.setParameter("deadline", deadline);
        query.executeUpdate();

        transaction.commit();
    }

    @Override
    public List<Sensor> getAll(List<Device> userDevices) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from Sensor s where s.device in (:devices)");
        query.setParameterList("devices", userDevices);
        List<Sensor> userSensors = query.list();

        transaction.commit();

        return userSensors;
    }

    public List<Sensor> getSensorValues(String sensorName, User user) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("select s from Sensor s inner join Device d on d = s.device where s.name = :sensorName and d.owner = :user");
        query.setParameter("user", user);
        query.setParameter("sensorName", sensorName);

        List<Sensor> sensors = query.list();

        transaction.commit();

        return sensors;
    }
}
