package intler_iot.services.converters.dto;

import intler_iot.controllers.entities.SensorPageDTO;
import intler_iot.dao.entities.Device;
import intler_iot.dao.entities.Sensor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class SensorPageDTOConverter {

    public SensorPageDTO covertToDTO(String sensorName, int pageNum, int pagesCount, List<Sensor> sensors) {
        SensorPageDTO sensorPageDTO = new SensorPageDTO();

        sensorPageDTO.setSensorName(sensorName);
        sensorPageDTO.setCurrentPage(pageNum);
        sensorPageDTO.setPagesCount(pagesCount);
        sensorPageDTO.setSensorsLogs(sensorsToPairs(sensors));

        return sensorPageDTO;
    }

    private List<Map.Entry<Timestamp, Double>> sensorsToPairs(List<Sensor> sensors) {
        List<Map.Entry<Timestamp, Double>> sensorPairs = new LinkedList<Map.Entry<Timestamp, Double>>();

        for (Sensor sensor : sensors) {
            sensorPairs.add(new AbstractMap.SimpleEntry<Timestamp, Double>(sensor.getArriveTime(), sensor.getValue()));
        }

        return sensorPairs;
    }
}
