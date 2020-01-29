package intler_iot.controllers.site;

import intler_iot.controllers.entities.SensorLog;
import intler_iot.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class SensorsLogsController {

    @Autowired
    SensorService sensorService;

    @GetMapping("console/get-sensors-logs")
    public List<SensorLog> getSensorLogs() {
        String login = "";
        String password = "";

        List<SensorLog> sensorLogs = sensorService.getUserSensors(login, password);

        return sensorLogs;
    }
}
