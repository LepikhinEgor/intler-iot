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

        Query query = session.createQuery("from Widget w where w.user = :user");
        query.setParameter("user", user);

        List<Widget> widgets = query.list();

        return widgets;
    }

    @Override
    public void recordWidgets(List<Widget> widgets) {
        Session session = sessionFactory.getCurrentSession();

        for (Widget widget : widgets) {
            session.save(widget);
        }
    }

    public void create(Widget widget) {
        Session session = sessionFactory.getCurrentSession();

        session.save(widget);
    }

    public void delete(long id) {
        Session session = sessionFactory.getCurrentSession();

        Widget widget = session.get(Widget.class, id);

        session.delete(widget);
    }

    @Override
    public void update(Widget widget) {
        Session session = sessionFactory.getCurrentSession();

        Widget oldWidget = session.get(Widget.class, widget.getId());
        oldWidget.setName(widget.getName());
        oldWidget.setColor(widget.getColor());
        oldWidget.setMeasure(widget.getMeasure());
        oldWidget.setKeyWard(widget.getKeyWard());
        oldWidget.setIcon(widget.getIcon());
        oldWidget.setType(widget.getType());
        oldWidget.setDeviceName(widget.getDeviceName());
        oldWidget.setMaxValue(widget.getMaxValue());
        oldWidget.setMinValue(widget.getMinValue());

        session.save(oldWidget);
    }

    @Override
    public void updateSize(Widget widget) {
        Session session = sessionFactory.getCurrentSession();

        Widget oldWidget = session.get(Widget.class, widget.getId());
        oldWidget.setWidth(widget.getWidth());
        oldWidget.setHeight(widget.getHeight());

        session.save(oldWidget);
    }

    @Override
    public void updateLastValue(String sensorName, String deviceName, double value) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("update Widget set lastValue = :value where deviceName = :deviceName and keyWard = :sensorName ");
        query.setParameter("deviceName", deviceName);
        query.setParameter("sensorName", sensorName);
        query.setParameter("value", value);

        query.executeUpdate();
    }
}
