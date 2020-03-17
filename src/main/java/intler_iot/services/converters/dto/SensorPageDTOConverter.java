package intler_iot.services.converters.dto;

import intler_iot.controllers.entities.SensorPageDTO;
import intler_iot.dao.entities.SensorValue;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class SensorPageDTOConverter {

    public SensorPageDTO covertToDTO(String sensorName, int pageNum, int pagesCount, List<SensorValue> sensorValues) {
        SensorPageDTO sensorPageDTO = new SensorPageDTO();

        sensorPageDTO.setSensorName(sensorName);
        sensorPageDTO.setCurrentPage(pageNum);
        sensorPageDTO.setPagesCount(pagesCount);
        sensorPageDTO.setSensorsLogs(sensorsToPairs(sensorValues));

        return sensorPageDTO;
    }

    private List<Map.Entry<Timestamp, Double>> sensorsToPairs(List<SensorValue> sensorValues) {
        List<Map.Entry<Timestamp, Double>> sensorPairs = new LinkedList<Map.Entry<Timestamp, Double>>();

        for (SensorValue sensorValue : sensorValues) {
            sensorPairs.add(new AbstractMap.SimpleEntry<Timestamp, Double>(sensorValue.getArriveTime(), sensorValue.getValue()));
        }

        return sensorPairs;
    }
}
