package intler_iot.dao.hibernate;

import intler_iot.dao.WidgetDao;
import intler_iot.dao.entities.User;
import intler_iot.dao.entities.Widget;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WidgetDaoHibernate extends WidgetDao {
    private static final Logger logger = LoggerFactory.getLogger(WidgetDaoHibernate.class);

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Widget> getWidgets(User user) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        try {
            Query query = session.createQuery("from Widget w where w.user = :user");
            query.setParameter("user", user);

            List<Widget> widgets = query.list();

            transaction.commit();

            return widgets;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            transaction.rollback();
            throw e;
        }

    }

    @Override
    public void recordWidgets(List<Widget> widgets) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        try {
            for (Widget widget : widgets) {
                session.save(widget);
            }

            transaction.commit();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public void update(Widget widget) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        try {
        Widget oldWidget = session.get(Widget.class, widget.getId());
        oldWidget.setName(widget.getName());
        oldWidget.setColor(widget.getColor());
        oldWidget.setMeasure(widget.getMeasure());
        oldWidget.setKeyWard(widget.getKeyWard());

        session.save(oldWidget);

        tx.commit();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            tx.rollback();
            throw e;
        }
    }
}
