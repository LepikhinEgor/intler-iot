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

        try {
            session.save(order);

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public List<CloudOrder> getDeviceOrders(Device device) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        try {
            Query getQuery = session.createQuery("FROM CloudOrder where device = :device and used = false");
            getQuery.setParameter("device", device);

            List<CloudOrder> orders = getQuery.list();

            Query updateQuery = session.createQuery("update CloudOrder set used = true where device = :device");
            updateQuery.setParameter("device", device);
            updateQuery.executeUpdate();

            transaction.commit();

            return orders;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public void deleteOld() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        try {
            Query query = session.createQuery("delete from CloudOrder where removed = true");

            query.executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public void markRemoved(List<String> ordersName, Device device) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        ordersName.forEach(s -> System.out.println(s));

        try {
            Query query = session.createQuery("update CloudOrder set removed = true where device = :device and keyWard in (:names)");
            query.setParameter("device", device);
            query.setParameter("names", ordersName);
            query.executeUpdate();

            query.executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

}
