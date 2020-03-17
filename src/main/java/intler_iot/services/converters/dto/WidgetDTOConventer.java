package intler_iot.services.converters.dto;

import intler_iot.controllers.entities.WidgetDTO;
import intler_iot.dao.entities.SensorValue;
import intler_iot.dao.entities.Widget;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WidgetDTOConventer {

    public List<WidgetDTO> convertToWidgetsDTO(List<Widget> widgets, List<SensorValue> sensorValues) {
        List<Map.Entry<Widget, SensorValue>> widgetSensorPairs = matchWidgetsSensors(widgets, sensorValues);
        List<WidgetDTO> widgetsList = convertToWidgetsDTO(widgetSensorPairs);

        widgetsList = sortWidgetsByOrder(widgetsList);
        return widgetsList;
    }

    private List<WidgetDTO>  sortWidgetsByOrder(List<WidgetDTO> widgetsDTO) {
        widgetsDTO.sort(Comparator.comparingLong((WidgetDTO wd) -> wd.getWidget().getId()));

        return widgetsDTO;
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

        widgetsData.sort(Comparator.comparingLong((WidgetDTO wd) -> wd.getWidget().getId()));
        return widgetsData;
    }
}
