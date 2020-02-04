package intler_iot.dao.hibernate;

import intler_iot.dao.WidgetDao;
import intler_iot.dao.entities.User;
import intler_iot.dao.entities.Widget;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WidgetDaoHibernate extends WidgetDao {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Widget> getWidgets(User user) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from Widget w where w.user = :user");
        query.setParameter("user", user);

        List<Widget> widgets = query.list();

        transaction.commit();

        return widgets;
    }

    @Override
    public void recordWidgets(List<Widget> widgets) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        for (Widget widget : widgets) {
            session.save(widget);
        }

        transaction.commit();
    }
}
