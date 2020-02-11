package intler_iot.dao;

import intler_iot.dao.entities.User;
import intler_iot.dao.entities.Widget;

import java.util.List;

public abstract class WidgetDao {
    public abstract List<Widget> getWidgets(User user);
    public abstract void recordWidgets(List<Widget> widgets);
    public abstract void update(Widget widget);
}
