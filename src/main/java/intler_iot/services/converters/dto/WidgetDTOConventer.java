package intler_iot.services.converters.dto;

import intler_iot.controllers.entities.WidgetDTO;
import intler_iot.dao.entities.Sensor;
import intler_iot.dao.entities.Widget;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WidgetDTOConventer {

    private List<WidgetDTO> convertToWidgetsDTO(List<Widget> widgets, List<Sensor> sensors) {
        List<Map.Entry<Widget,Sensor>> widgetSensorPairs = matchWidgetsSensors();

        widgetsData.sort(Comparator.comparingLong((WidgetDTO wd) -> wd.getWidget().getId()));
        return widgetsData;
    }

    private List<Map.Entry<Widget, Sensor>> matchWidgetsSensors(List<Widget> widgets, List<Sensor> sensors) {
        List<Map.Entry<Widget,Sensor>> pairs = new ArrayList<>();
        for (Widget widget: widgets) {
            Sensor foundSensor = null;
            for (Sensor sensor : sensors) {
                if (widget.getKeyWard().equals(sensor.getName())) {
                    foundSensor = sensor;
                    break;
                }
            }
            pairs.add(new AbstractMap.SimpleEntry<>(widget, foundSensor));
        }

        return pairs;
    }

    private List<WidgetDTO> convertToWidgetsDTO(List<Map.Entry<Widget,Sensor>> widgetSensorPairs) {
        List<WidgetDTO> widgetsData = new ArrayList<>();
        for (Map.Entry<Widget,Sensor> pair: widgetSensorPairs) {
            WidgetDTO widgetDTO = new WidgetDTO(pair.getKey(), pair.getValue());

            widgetsData.add(widgetDTO);
        }

        widgetsData.sort(Comparator.comparingLong((WidgetDTO wd) -> wd.getWidget().getId()));
        return widgetsData;
    }
}
