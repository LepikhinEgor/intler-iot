package intler_iot.dao.hibernate;

import intler_iot.dao.SensorDao;
import intler_iot.dao.entities.Device;
import intler_iot.dao.entities.SensorValue;
import intler_iot.dao.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    public void create(SensorValue sensorValue) {
        Session session = sessionFactory.getCurrentSession();

        session.save(sensorValue);
    }

    @Override
    public void recordAll(List<SensorValue> sensorValues) {
        Session session = sessionFactory.getCurrentSession();

        for (SensorValue sensorValue : sensorValues)
            session.save(sensorValue);

    }

    @Override
    public void removeOldValues(Timestamp deadline) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("delete from SensorValue where arriveTime < :deadline");
        query.setParameter("deadline", deadline);
        query.executeUpdate();
    }

    @Override
    public List<SensorValue> getAll(List<Device> userDevices) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("from SensorValue s where s.device in (:devices)");
        query.setParameterList("devices", userDevices);

        List<SensorValue> userSensorValues = query.list();

        return userSensorValues;
    }

    public List<SensorValue> getSensorValues(String sensorName, User user) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("select s from SensorValue s inner join Device d on d = s.device where s.name = :sensorName and d.owner = :user");
        query.setParameter("user", user);
        query.setParameter("sensorName", sensorName);

        List<SensorValue> sensorValues = query.list();


        return sensorValues;
    }

    @Override
    public List<SensorValue> getLastSensors(User user) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createSQLQuery("select * from sensors s1 \n" +
                "                inner join (select s1.name, max(s1.id) as sens_id from sensors s1 group by s1.name) s2\n" +
                "                on s1.name = s2.name and s1.id = s2.sens_id\n" +
                "                inner join devices d on s1.device_id = d.id where d.owner_id  = :user_id").addEntity(SensorValue.class);
        query.setParameter("user_id", user.getId());

        List<SensorValue> lastSensorValues = query.list();

        return lastSensorValues;
    }
}
