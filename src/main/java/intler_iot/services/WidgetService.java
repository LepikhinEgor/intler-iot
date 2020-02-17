package intler_iot.services;

import intler_iot.controllers.entities.WidgetData;
import intler_iot.dao.WidgetDao;
import intler_iot.dao.entities.Sensor;
import intler_iot.dao.entities.User;
import intler_iot.dao.entities.Widget;
import intler_iot.services.exceptions.NotAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WidgetService {

    private UserService userService;
    private SensorService sensorService;

    private WidgetDao widgetDao;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setSensorService(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Autowired
    public void setWidgetDao(WidgetDao widgetDao) {
        this.widgetDao = widgetDao;
    }


    public void updateWidget(Widget widget) {
        widgetDao.update(widget);
    }

    public List<WidgetData> getWidgetsList() throws NotAuthException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Sensor> lastSensors = sensorService.getLastSensors(user);
        List<Widget> widgets = widgetDao.getWidgets(user);

        List<Sensor> newSensors = getSensorsWithoutWidget(lastSensors, widgets);
        List<Widget> createdWidgets = createWidgets(newSensors);
        widgetDao.recordWidgets(createdWidgets);

        widgets.addAll(createdWidgets);

        List<WidgetData> widgetsData = transformToWidgetsData(widgets, lastSensors);

        return widgetsData;
    }

    private List<Sensor> getSensorsWithoutWidget(List<Sensor> lastSensors,List<Widget> widgets) {
        List<Sensor> newSensors = new ArrayList<>();
        for (Sensor sensor : lastSensors) {
            boolean isExist = false;
            for (Widget widget: widgets) {
                if (sensor.getName().equals(widget.getKeyWard())) {
                    isExist = true;
                }
            }
            if (!isExist)
                newSensors.add(sensor);
        }

        return newSensors;
    }

    private List<Widget> createWidgets(List<Sensor> newSensors) {
        List<Widget> widgets = new ArrayList<>();

        for (Sensor sensor: newSensors) {
            widgets.add(new Widget(sensor));
        }

        return widgets;
    }

    private List<WidgetData> transformToWidgetsData(List<Widget> widgets, List<Sensor> lastSensors) {
        List<WidgetData> widgetsData = new ArrayList<>();
        for (Widget widget: widgets) {
            WidgetData widgetData = null;
            for (Sensor sensor : lastSensors) {
                if (widget.getKeyWard().equals(sensor.getName())) {
                    widgetData = new WidgetData(widget, sensor);
                    break;
                }
            }
            if (widget != null)
                widgetsData.add(widgetData);
            else
                throw new RuntimeException("Not match sensor and widget");
        }
        return widgetsData;
    }
}
