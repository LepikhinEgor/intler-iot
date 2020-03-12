package intler_iot.dao;

import intler_iot.dao.entities.User;
import intler_iot.dao.entities.Widget;

import java.util.List;

public abstract class WidgetDao {
    public abstract void create(Widget widget);
    public abstract void delete(long id);
    public abstract List<Widget> getWidgets(User user);
    public abstract void recordWidgets(List<Widget> widgets);
    public abstract void update(Widget widget);
    public abstract void updateSize(Widget widget);
    public abstract void updateLastValue(String sensorName, String deviceName, double value);
}
