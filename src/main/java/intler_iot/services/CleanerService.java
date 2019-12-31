package intler_iot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class CleanerService {

    private SensorService sensorService;
    private CloudOrderService cloudOrderService;

    @Autowired
    public void setSensorService(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Autowired
    public void setCloudOrderService(CloudOrderService cloudOrderService) {
        this.cloudOrderService = cloudOrderService;
    }

    @Scheduled(cron = "0 0 12 * * ?")
    public void removeOldSensorsVal() {
        sensorService.removeOldSensorsValue();
    }

    @Scheduled(cron = "0 0 12 * * ?")
    public void cleanOldCloudOrders() {
        cloudOrderService.deleteOldOrders();
    }
}
