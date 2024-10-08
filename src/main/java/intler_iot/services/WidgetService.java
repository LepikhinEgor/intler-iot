package intler_iot.services;

import intler_iot.controllers.entities.WidgetDTO;
import intler_iot.controllers.entities.WidgetSizeDTO;
import intler_iot.dao.WidgetDao;
import intler_iot.dao.entities.SensorValue;
import intler_iot.dao.entities.User;
import intler_iot.dao.entities.Widget;
import intler_iot.services.converters.dto.WidgetDTOConverter;
import intler_iot.services.exceptions.NotAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class WidgetService {

    private SensorService sensorService;
    private UserService userService;

    private WidgetDao widgetDao;

    private WidgetDTOConverter widgetDTOConverter;

    @Autowired
    public void setSensorService(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Autowired
    public void setWidgetDao(WidgetDao widgetDao) {
        this.widgetDao = widgetDao;
    }

    @Autowired
    public void setWidgetDTOConverter(WidgetDTOConverter widgetDTOConverter) {
        this.widgetDTOConverter = widgetDTOConverter;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    public void createWidget(Widget widget) {
        User user = userService.getCurrentUser();

        widget.setUser(user);
        widget.setWidth(Widget.DEFAULT_WIDTH);
        widget.setHeight(Widget.DEFAULT_HEIGHT);

        widgetDao.create(widget);
    }

    @Transactional
    public void deleteWidget(long id) {
        widgetDao.delete(id);
    }

    @Transactional
    public void updateWidget(Widget widget) {
        widgetDao.update(widget);
    }

    /***
     * Method return list of old and new user widgets as WidgetDTO list.
     * This method constantly calls by site every N seconds, when dashboard page is open
     * @return all user widgets as WidgetDTO list
     * @throws NotAuthException
     */
    @Transactional
    public List<WidgetDTO> getWidgetsList() throws NotAuthException {
        User user = userService.getCurrentUser();

        List<SensorValue> lastSensorValues = sensorService.getLastSensors(user);
        List<Widget> widgets = getAllWidgets(user, lastSensorValues);

        List<WidgetDTO> widgetsDTO = widgetDTOConverter.convertToWidgetsDTO(widgets, lastSensorValues);
        widgetsDTO = sortWidgetsByOrder(widgetsDTO);

        return widgetsDTO;
    }

    /***
     * Update last value which was showing on widget. It necessary for sliders, because need save last changed slider value
     * otherwise slider value will be reset every time by getWidgets request
     * @param sensorName
     * @param deviceName
     * @param value
     */
    @Transactional
    public void updateWidgetLastValue(String sensorName, String deviceName, double value) {
        widgetDao.updateLastValue(sensorName, deviceName, value);
    }

    /**
     * Method save new widgets size to database
     * @param widgetsSize
     */
    @Transactional
    public void updateWidgetsSize(List<WidgetSizeDTO> widgetsSize) {
        for (WidgetSizeDTO widgetSizeDTO : widgetsSize) {
            Widget widget = new Widget();
            widget.setId(widgetSizeDTO.getId());
            widget.setWidth(widgetSizeDTO.getWidth());
            widget.setHeight(widgetSizeDTO.getHeight());

            widgetDao.updateSize(widget);
        }
    }

    private List<WidgetDTO>  sortWidgetsByOrder(List<WidgetDTO> widgetsDTO) {
        widgetsDTO.sort(Comparator.comparingLong((WidgetDTO wd) -> wd.getId()));

        return widgetsDTO;
    }

    /***
     * Method for getting user widgets.  If since last request db receive new sensor data
     *  will be created new widgets with default parameters.
     * @param user
     * @param lastSensorValues list of all unique sensor with actual(latest) value
     * @return
     */
    private List<Widget> getAllWidgets(User user, List<SensorValue> lastSensorValues) {
        List<Widget> widgets = widgetDao.getWidgets(user);

        List<SensorValue> newSensorValues = getSensorsWithoutWidget(lastSensorValues, widgets);
        List<Widget> createdWidgets = createWidgets(newSensorValues);
        widgetDao.recordWidgets(createdWidgets);

        widgets.addAll(createdWidgets);

        return widgets;
    }

    /**
     * Method return list of new sensors which not showing by any widget
     * @param lastSensorValues list of all unique sensor with actual(latest) value
     * @param widgets old widgets from db
     * @return
     */
    List<SensorValue> getSensorsWithoutWidget(List<SensorValue> lastSensorValues, List<Widget> widgets) {
        List<SensorValue> newSensorValues = new ArrayList<>();
        for (SensorValue sensorValue : lastSensorValues) {
            boolean isExist = false;
            for (Widget widget: widgets) {
                if (sensorValue.getName().equals(widget.getKeyWard())) {
                    isExist = true;
                }
            }
            if (!isExist)
                newSensorValues.add(sensorValue);
        }

        return newSensorValues;
    }


    /**
     * Method create new widgets by last sensors value
     * @param newSensorValues
     * @return
     */
    private List<Widget> createWidgets(List<SensorValue> newSensorValues) {
        List<Widget> widgets = new ArrayList<>();

        for (SensorValue sensorValue : newSensorValues) {
            widgets.add(new Widget(sensorValue));
        }

        return widgets;
    }
}
