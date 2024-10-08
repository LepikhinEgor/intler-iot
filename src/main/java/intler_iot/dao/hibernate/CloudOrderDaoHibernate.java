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

        Query getOldOrder = session.createQuery("from CloudOrder where keyWard = :keyWord and used != true and device = :device order by id").setMaxResults(1);
        getOldOrder.setParameter("keyWord", order.getKeyWard());
        getOldOrder.setParameter("device", order.getDevice());

        CloudOrder oldOrder = (CloudOrder)getOldOrder.uniqueResult();
        if (oldOrder != null) {
            oldOrder.setValue(order.getValue());
            session.update(oldOrder);
        } else {
            session.save(order);
        }
    }

    @Override
    public List<CloudOrder> getDeviceOrders(Device device) {
        Session session = sessionFactory.getCurrentSession();

        Query getQuery = session.createQuery("FROM CloudOrder where device = :device and used = false");
        getQuery.setParameter("device", device);

        List<CloudOrder> orders = getQuery.list();

        Query updateQuery = session.createQuery("update CloudOrder set used = true where device = :device");
        updateQuery.setParameter("device", device);
        updateQuery.executeUpdate();

        return orders;
    }

    @Override
    public void deleteOld() {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("delete from CloudOrder where removed = true");

        query.executeUpdate();
    }

    @Override
    public void markRemoved(List<String> ordersName, Device device) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("update CloudOrder set removed = true where device = :device and keyWard in (:names)");
        query.setParameter("device", device);
        query.setParameter("names", ordersName);
        query.executeUpdate();

        query.executeUpdate();
    }

}
