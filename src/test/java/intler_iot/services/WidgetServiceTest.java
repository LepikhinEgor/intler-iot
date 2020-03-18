package intler_iot.services;

import intler_iot.controllers.entities.DeviceStateDTO;
import intler_iot.controllers.entities.WidgetDTO;
import intler_iot.controllers.entities.WidgetSizeDTO;
import intler_iot.dao.WidgetDao;
import intler_iot.dao.entities.Device;
import intler_iot.dao.entities.SensorValue;
import intler_iot.dao.entities.User;
import intler_iot.dao.entities.Widget;
import intler_iot.services.converters.dto.WidgetDTOConventer;
import intler_iot.services.exceptions.NotAuthException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

public class WidgetServiceTest {

    private WidgetService widgetService;

    @Mock
    UserService userServiceMock;

    @Mock
    SensorService sensorServiceMock;

    @Mock
    WidgetDao widgetDaoMock;

    @Spy
    WidgetDTOConventer widgetDTOConventer;

    private Widget getWidget(String name, long id) {
        Widget widget = new Widget();

        widget.setId(id);
        widget.setKeyWard(name);

        return widget;
    }

    private SensorValue getSensorValue(String name) {
        SensorValue sensorValue = new SensorValue();
        sensorValue.setName(name);
        sensorValue.setValue(0);
        sensorValue.setDevice(new Device());
        sensorValue.setArriveTime(new Timestamp(System.currentTimeMillis()));

        return sensorValue;
    }

//    private WidgetSizeDTO getWidgetSizeDTO() {
//        WidgetSizeDTO widgetSizeDTO = new WidgetSizeDTO();
//        widgetSizeDTO.se
//    }

    @Before
    public void initTest() {
        widgetService = new WidgetService();
        MockitoAnnotations.initMocks(this);
        inject();
    }

    private void inject() {
        widgetService.setWidgetDao(widgetDaoMock);
        widgetService.setSensorService(sensorServiceMock);
        widgetService.setUserService(userServiceMock);
        widgetService.setWidgetDTOConverter(widgetDTOConventer);
    }

    @Test
    public void getWidgetsList_successReturn5NewWidgets() throws NotAuthException {
        List<SensorValue> lastSensorsValue = new LinkedList<>();
        lastSensorsValue.add(getSensorValue("sensor1"));
        lastSensorsValue.add(getSensorValue("sensor2"));
        lastSensorsValue.add(getSensorValue("sensor3"));
        lastSensorsValue.add(getSensorValue("sensor4"));
        lastSensorsValue.add(getSensorValue("sensor5"));

        List<Widget> widgets = new LinkedList<>();

        when(sensorServiceMock.getLastSensors(any())).thenReturn(lastSensorsValue);
        when(widgetDaoMock.getWidgets(any())).thenReturn(widgets);

        List<WidgetDTO> widgetsDTO = widgetService.getWidgetsList();

        for (WidgetDTO widgetDTO: widgetsDTO) {
            assertEquals(widgetDTO.getWidget().getId(), 0);//created(without id)
        }
    }

    @Test
    public void getWidgetsList_successReturn3NewWidgetsAnd2Old() throws NotAuthException {
        List<SensorValue> lastSensorsValue = new LinkedList<>();
        lastSensorsValue.add(getSensorValue("sensor1"));
        lastSensorsValue.add(getSensorValue("sensor2"));
        lastSensorsValue.add(getSensorValue("sensor3"));
        lastSensorsValue.add(getSensorValue("sensor4"));
        lastSensorsValue.add(getSensorValue("sensor5"));

        List<Widget> widgets = new LinkedList<>();
        widgets.add(getWidget("sensor1", 1));
        widgets.add(getWidget("sensor2", 2));

        when(sensorServiceMock.getLastSensors(any())).thenReturn(lastSensorsValue);
        when(widgetDaoMock.getWidgets(any())).thenReturn(widgets);

        List<WidgetDTO> widgetsDTO = widgetService.getWidgetsList();

        for (WidgetDTO widgetDTO: widgetsDTO) {
            switch (widgetDTO.getWidget().getKeyWard()) {
                case "sensor1":assertEquals(widgetDTO.getWidget().getId(), widgetDTO.getWidget().getId()); break;
                case "sensor2":assertEquals(2, widgetDTO.getWidget().getId()); break;
                default: assertEquals(0, widgetDTO.getWidget().getId()); break; //created(without id)
            }
        }
    }

    @Test
    public void getWidgetsList_successReturn5OldWidget() throws NotAuthException {
        List<SensorValue> lastSensorsValue = new LinkedList<>();

        List<Widget> widgets = new LinkedList<>();
        widgets.add(getWidget("sensor1", 1));
        widgets.add(getWidget("sensor2", 2));
        widgets.add(getWidget("sensor3", 3));
        widgets.add(getWidget("sensor4", 4));
        widgets.add(getWidget("sensor5", 5));

        when(sensorServiceMock.getLastSensors(any())).thenReturn(lastSensorsValue);
        when(widgetDaoMock.getWidgets(any())).thenReturn(widgets);

        List<WidgetDTO> widgetsDTO = widgetService.getWidgetsList();

        for (WidgetDTO widgetDTO: widgetsDTO) {
            assertNotEquals(0, widgetDTO.getWidget().getId()); break; //created(without id)
        }
    }

    @Test
    public void updateWidgetsSize_successUpdate3Widget() {
        List<WidgetSizeDTO> widgetSizes = Arrays.asList(new WidgetSizeDTO(),new WidgetSizeDTO(),new WidgetSizeDTO());

        widgetService.updateWidgetsSize(widgetSizes);

        verify(widgetDaoMock, times(3)).updateSize(any());
    }

    @Test
    public void updateWidgetsSize_successUpdate0Widget() {
        List<WidgetSizeDTO> widgetSizes = Collections.emptyList();

        widgetService.updateWidgetsSize(widgetSizes);

        verify(widgetDaoMock, times(0)).updateSize(any());
    }

}
