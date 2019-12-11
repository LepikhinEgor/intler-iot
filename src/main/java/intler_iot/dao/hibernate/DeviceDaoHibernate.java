package intler_iot.dao.hibernate;

import intler_iot.dao.DeviceDao;
import intler_iot.dao.entities.Device;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DeviceDaoHibernate extends DeviceDao {

    SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void connectDevice(Device device) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        session.saveOrUpdate(device);

        transaction.commit();
        session.close();
    }
}
