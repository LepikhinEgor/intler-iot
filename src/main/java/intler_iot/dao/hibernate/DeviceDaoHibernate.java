package intler_iot.dao.hibernate;

import intler_iot.dao.DeviceDao;
import intler_iot.dao.entities.Device;
import intler_iot.dao.entities.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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

        Query getDeviceQuery = session.createQuery("from Device where name = :name and owner = :user");
        getDeviceQuery.setParameter("user", device.getOwner().getId());
        getDeviceQuery.setParameter("name", device.getName());
        List<Device> foundDevices = null;
        try {
            foundDevices = getDeviceQuery.list();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println("Passed");

        if (foundDevices.isEmpty())
            session.save(device);
        else {
            Query query = session.createQuery("update Device set type = :deviceType where name = :name and owner = :user");
            query.setParameter("deviceType", device.getType());
            query.setParameter("name", device.getName());
            query.setParameter("user", device.getOwner().getId());
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
