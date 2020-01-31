package intler_iot.controllers.site;

import intler_iot.controllers.entities.SensorLog;
import intler_iot.services.SensorService;
import intler_iot.services.exceptions.NotAuthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SensorsLogsController {

    private static final Logger logger = LoggerFactory.getLogger(SensorsLogsController.class);

    private SensorService sensorService;

    @Autowired
    public void setSensorService(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @GetMapping("console/get-sensors-logs")
    @ResponseBody
    public List<SensorLog> getSensorLogs() {
        String login = "admin";
        String password = "qwerty";

        List<SensorLog> sensorLogs;
        try {
            sensorLogs = sensorService.getUserSensors(login, password);
        } catch (NotAuthException e) {
            logger.error(e.getMessage(),e);
            return  new ArrayList<SensorLog>();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return null;
        }
        return sensorLogs;
    }

    @GetMapping("console/logs")
    public String getSensorsLogPage() {
        return "sensorsLogs";
    }

    @GetMapping("console/get-sensor-logs-page")
    @ResponseBody
    public SensorLog getSensorLogsPage(@RequestParam("name") String sensorName, @RequestParam("pageNum") int pageNum) {
        String login = "admin";
        String password = "qwerty";

        SensorLog sensorLog = null;
        try {
            sensorLog = sensorService.getSensorLogPage(login, password, sensorName, pageNum);
        } catch (Exception e) {
            logger.info(e.getMessage(),e);
        }
        return sensorLog;
    }
}
