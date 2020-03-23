package intler_iot.services.converters.dto;

import intler_iot.controllers.entities.WidgetDTO;
import intler_iot.dao.entities.SensorValue;
import intler_iot.dao.entities.User;
import intler_iot.dao.entities.Widget;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class WidgetDTOConverterTest {
    private WidgetDTOConverter widgetDTOConverter = new WidgetDTOConverter();

    private Widget getWidget(String keyword) {
        Widget widget = new Widget();
        widget.setMinValue(5);
        widget.setMaxValue(100);
        widget.setDeviceName("deviceName");
        widget.setHeight(200);
        widget.setWidth(200);
        widget.setUser(new User());
        widget.setType(5);
        widget.setId(1);
        widget.setKeyWard(keyword);
        widget.setIcon(5);
        widget.setColor(5);
        widget.setLastValue(5);

        return widget;
    }

    private SensorValue getSensorValue(String name) {
        SensorValue sensorValue = new SensorValue();

        sensorValue.setName(name);
        sensorValue.setValue(4);
        sensorValue.setArriveTime(new Timestamp((System.currentTimeMillis())));
        sensorValue.setId(1);

        return sensorValue;
    }

    @Test
    public void convertToWidgetsDTO_successConvert2WidgetsWithValue() {
        List<Widget> widgets = Arrays.asList(getWidget("sens1"), getWidget("sens2"));
        List<SensorValue> sensorsValue = Arrays.asList(getSensorValue("sens1"), getSensorValue("sens2"));

        List<WidgetDTO> widgetsDto = widgetDTOConverter.convertToWidgetsDTO(widgets, sensorsValue);
        assertEquals(2, widgetsDto.size());
        assertTrue(widgetsDto.get(0).isHasValue());
        assertTrue(widgetsDto.get(1).isHasValue());
    }

    @Test
    public void convertToWidgetsDTO_successConvert1WidgetWithValueAnd1WithoutValue() {
        List<Widget> widgets = Arrays.asList(getWidget("sens1"), getWidget("sens2"));
        List<SensorValue> sensorsValue = Arrays.asList(getSensorValue("sens1"));

        List<WidgetDTO> widgetsDto = widgetDTOConverter.convertToWidgetsDTO(widgets, sensorsValue);
        assertEquals(2, widgetsDto.size());
        assertTrue(widgetsDto.get(0).isHasValue());
        assertFalse(widgetsDto.get(1).isHasValue());
    }

    @Test
    public void convertToWidgetsDTO_successConvert2WidgetsWithoutValue() {
        List<Widget> widgets = Arrays.asList(getWidget("sens1"), getWidget("sens2"));
        List<SensorValue> sensorsValue = Arrays.asList();

        List<WidgetDTO> widgetsDto = widgetDTOConverter.convertToWidgetsDTO(widgets, sensorsValue);
        assertEquals(2, widgetsDto.size());
        assertFalse(widgetsDto.get(0).isHasValue());
        assertFalse(widgetsDto.get(1).isHasValue());
    }
}
