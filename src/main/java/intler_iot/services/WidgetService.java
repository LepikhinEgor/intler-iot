package intler_iot.services;

import intler_iot.controllers.entities.WidgetDTO;
import intler_iot.controllers.entities.WidgetSizeDTO;
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

    private SensorService sensorService;

    private WidgetDao widgetDao;

    private WidgetDTOConventer widgetDTOConventer;

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

    /***
     * Method return list of old and new user widgets as WidgetDTO list.
     * This method constantly calls by site every N seconds, when dashboard page is open
     * @return all user widgets as WidgetDTO list
     * @throws NotAuthException
     */
    public List<WidgetDTO> getWidgetsList() throws NotAuthException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Sensor> lastSensors = sensorService.getLastSensors(user);
        List<Widget> widgets = getAllWidgets(user, lastSensors);

        List<WidgetDTO> widgetsDTO = widgetDTOConventer.convertToWidgetsDTO(widgets, lastSensors);

        return widgetsDTO;
    }

    /***
     * Update last value which was showing on widget. It necessary for sliders, because need save last changed slider value
     * otherwise slider value will be reset every time by getWidgets request
     * @param sensorName
     * @param deviceName
     * @param value
     */
    public void updateWidgetLastValue(String sensorName, String deviceName, double value) {
        widgetDao.updateLastValue(sensorName, deviceName, value);
    }

    /**
     * Method save new widgets size to database
     * @param widgetsSize
     */
    public void updateWidgetsSize(List<WidgetSizeDTO> widgetsSize) {
        for (WidgetSizeDTO widgetSizeDTO : widgetsSize) {
            Widget widget = new Widget();
            widget.setId(widgetSizeDTO.getId());
            widget.setWidth(widgetSizeDTO.getWidth());
            widget.setHeight(widgetSizeDTO.getHeight());

            widgetDao.updateSize(widget);
        }
    }

    /***
     * Method for getting user widgets.  If since last request db receive new sensor data
     *  will be created new widgets with default parameters.
     * @param user
     * @param lastSensors list of all unique sensor with actual(latest) value
     * @return
     */
    private List<Widget> getAllWidgets(User user, List<Sensor> lastSensors ) {
        List<Widget> widgets = widgetDao.getWidgets(user);

        List<Sensor> newSensors = getSensorsWithoutWidget(lastSensors, widgets);
        List<Widget> createdWidgets = createWidgets(newSensors);
        widgetDao.recordWidgets(createdWidgets);

        widgets.addAll(createdWidgets);

        return widgets;
    }

    /**
     * Method return list of new sensors which not showing by any widget
     * @param lastSensors list of all unique sensor with actual(latest) value
     * @param widgets old widgets from db
     * @return
     */
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


    /**
     * Method create new widgets by last sensors value
     * @param newSensors
     * @return
     */
    private List<Widget> createWidgets(List<Sensor> newSensors) {
        List<Widget> widgets = new ArrayList<>();

        for (Sensor sensor: newSensors) {
            widgets.add(new Widget(sensor));
        }

        return widgets;
    }
}
