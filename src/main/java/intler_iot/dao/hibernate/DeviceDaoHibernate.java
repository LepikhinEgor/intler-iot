package intler_iot.dao.hibernate;

import intler_iot.dao.DeviceDao;
import intler_iot.dao.entities.Device;
import intler_iot.dao.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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

        Query getDeviceQuery = session.createQuery("from Device d where name = :name and d.owner = :owner");
        getDeviceQuery.setParameter("owner", device.getOwner());
        getDeviceQuery.setParameter("name", device.getName());
        List<Device> foundDevices = getDeviceQuery.list();

        if (foundDevices.isEmpty()) {
            session.save(device);
        }
        else {
            Query query = session.createQuery("update Device set lastDeviceMessageTime = :time where name = :name and owner = :user");
            query.setParameter("time", device.getLastDeviceMessageTime());
            query.setParameter("name", device.getName());
            query.setParameter("user", device.getOwner());
        }

        transaction.commit();
        session.close();
    }

    @Override
    public Device getUserDeviceByName(String name, User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("FROM Device WHERE owner = :ownerId AND name = :name");
        query.setParameter("ownerId", user.getId());
        query.setParameter("name", name);
        Device foundDevice = (Device)query.list().get(0);

        transaction.commit();
        session.close();

        return foundDevice;
    }
}
