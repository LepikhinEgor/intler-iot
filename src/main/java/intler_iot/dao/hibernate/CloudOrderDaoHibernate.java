package intler_iot.dao.hibernate;

import intler_iot.dao.CloudOrderDao;
import intler_iot.dao.entities.CloudOrder;
import intler_iot.dao.entities.Device;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CloudOrderDaoHibernate extends CloudOrderDao {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(CloudOrder order) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        session.save(order);

        transaction.commit();
    }

    @Override
    public List<CloudOrder> getDeviceOrders(Device device) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Query getQuery = session.createQuery("FROM CloudOrder where device = :device");
        getQuery.setParameter("device", device);

        List<CloudOrder> orders = getQuery.list();

        Query updateQuery = session.createQuery("update CloudOrder set used = true where device = :device");
        updateQuery.setParameter("device", device);
        updateQuery.executeUpdate();

        transaction.commit();

        return orders;
    }
}
