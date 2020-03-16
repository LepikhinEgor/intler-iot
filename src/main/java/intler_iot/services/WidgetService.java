package intler_iot.services;

import intler_iot.controllers.entities.WidgetDTO;
import intler_iot.controllers.entities.WidgetSize;
import intler_iot.dao.WidgetDao;
import intler_iot.dao.entities.Sensor;
import intler_iot.dao.entities.User;
import intler_iot.dao.entities.Widget;
import intler_iot.services.converters.dto.WidgetDTOConventer;
import intler_iot.services.exceptions.NotAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WidgetService {

    private UserService userService;
    private SensorService sensorService;

    private WidgetDao widgetDao;

    private WidgetDTOConventer widgetDTOConventer;

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

    @Autowired
    public void setWidgetDUOConverter(WidgetDTOConventer widgetDTOConventer) {
        this.widgetDTOConventer = widgetDTOConventer;
    }

    public void createWidget(Widget widget) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        widget.setUser(user);
        widget.setWidth(Widget.DEFAULT_WIDTH);
        widget.setHeight(Widget.DEFAULT_HEIGHT);

        widgetDao.create(widget);
    }

    public void deleteWidget(long id) {
        widgetDao.delete(id);
    }

    public void updateWidget(Widget widget) {
        widgetDao.update(widget);
    }

    public List<WidgetDTO> getWidgetsList() throws NotAuthException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Sensor> lastSensors = sensorService.getLastSensors(user);
        List<Widget> widgets = getAllWidgets(user, lastSensors);

        List<WidgetDTO> widgetsDTO = widgetDTOConventer.convertToWidgetsDTO(widgets, lastSensors);

        return widgetsDTO;
    }

    public void updateWidgetLastValue(String sensorName, String deviceName, double value) {
        widgetDao.updateLastValue(sensorName, deviceName, value);
    }

    private List<Widget> getAllWidgets(User user, List<Sensor> lastSensors ) {
        List<Widget> widgets = widgetDao.getWidgets(user);

        List<Sensor> newSensors = getSensorsWithoutWidget(lastSensors, widgets);
        List<Widget> createdWidgets = createWidgets(newSensors);
        widgetDao.recordWidgets(createdWidgets);

        widgets.addAll(createdWidgets);

        return widgets;
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

    public void updateWidgetsSize(List<WidgetSize> widgetsSize) {
        for (WidgetSize widgetSize : widgetsSize) {
            Widget widget = new Widget();
            widget.setId(widgetSize.getId());
            widget.setWidth(widgetSize.getWidth());
            widget.setHeight(widgetSize.getHeight());

            widgetDao.updateSize(widget);
        }
    }

    private List<Widget> createWidgets(List<Sensor> newSensors) {
        List<Widget> widgets = new ArrayList<>();

        for (Sensor sensor: newSensors) {
            widgets.add(new Widget(sensor));
        }

        return widgets;
    }
}
