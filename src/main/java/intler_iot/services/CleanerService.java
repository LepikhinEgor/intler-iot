package intler_iot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class CleanerService {

    private SensorService sensorService;

    @Autowired
    public void setSensorService(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Scheduled(cron = "0 0 13 * * ?")
    public void removeOldSensorsVal() {
        sensorService.removeOldSensorsValue();
    }
}
