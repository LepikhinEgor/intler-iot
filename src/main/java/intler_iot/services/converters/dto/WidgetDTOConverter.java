package intler_iot.services.converters.dto;

import intler_iot.controllers.entities.WidgetDTO;
import intler_iot.dao.entities.SensorValue;
import intler_iot.dao.entities.Widget;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WidgetDTOConverter {

    public Widget convertToWidget(WidgetDTO widgetDTO) {
        Widget widget = new Widget();
        widget.setId(widgetDTO.getId());
        widget.setKeyWard(widgetDTO.getKeyword());

        if (widgetDTO.isHasValue())
            widget.setLastValue(widgetDTO.getValue());
        else
            widget.setLastValue(widgetDTO.getLastValue());

        widget.setMaxValue(widgetDTO.getMaxValue());
        widget.setMinValue(widgetDTO.getMinValue());
        widget.setHeight(widgetDTO.getHeight());
        widget.setWidth(widgetDTO.getWidth());
        widget.setIcon(widgetDTO.getIcon());
        widget.setColor(widgetDTO.getColor());
        widget.setDeviceName(widgetDTO.getDeviceName());
        widget.setType(widgetDTO.getType());
        widget.setName(widgetDTO.getName());

        return widget;
    }

    public List<WidgetDTO> convertToWidgetsDTO(List<Widget> widgets, List<SensorValue> sensorValues) {
        List<Map.Entry<Widget, SensorValue>> widgetSensorPairs = matchWidgetsSensors(widgets, sensorValues);
        List<WidgetDTO> widgetsList = convertToWidgetsDTO(widgetSensorPairs);

        return widgetsList;
    }


    private List<Map.Entry<Widget, SensorValue>> matchWidgetsSensors(List<Widget> widgets, List<SensorValue> sensorValues) {
        List<Map.Entry<Widget, SensorValue>> pairs = new ArrayList<>();
        for (Widget widget: widgets) {
            SensorValue foundSensorValue = null;
            for (SensorValue sensorValue : sensorValues) {
                if (widget.getKeyWard().equals(sensorValue.getName())) {
                    foundSensorValue = sensorValue;
                    break;
                }
            }
            pairs.add(new AbstractMap.SimpleEntry<>(widget, foundSensorValue));
        }

        return pairs;
    }

    private List<WidgetDTO> convertToWidgetsDTO(List<Map.Entry<Widget, SensorValue>> widgetSensorPairs) {
        List<WidgetDTO> widgetsData = new ArrayList<>();
        for (Map.Entry<Widget, SensorValue> pair: widgetSensorPairs) {
            WidgetDTO widgetDTO = new WidgetDTO(pair.getKey(), pair.getValue());

            widgetsData.add(widgetDTO);
        }

        return widgetsData;
    }
}
