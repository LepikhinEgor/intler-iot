package intler_iot.services.converters.dto;

import intler_iot.controllers.entities.SensorPageDTO;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SensorDTOConverterTest {
    private SensorPageDTOConverter sensorPageDTOConverter = new SensorPageDTOConverter();

    @Test
    public void covertToDTO() {
        SensorPageDTO pageDTO = sensorPageDTOConverter.covertToDTO("name", 0, 0, new ArrayList<>());

        assertEquals("name", pageDTO.getSensorName());
        assertEquals(0, pageDTO.getCurrentPage());
        assertEquals(0, pageDTO.getPagesCount());
        assertNotNull(pageDTO.getSensorsLogs());
    }
}
