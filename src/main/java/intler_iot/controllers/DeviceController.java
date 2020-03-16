package intler_iot.controllers;

import intler_iot.controllers.entities.DeviceStateDTO;
import intler_iot.services.CloudOrderService;
import intler_iot.services.ControlCommandService;
import intler_iot.services.DeviceService;
import intler_iot.services.SensorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class DeviceController {

    private static final Logger logger = LoggerFactory.getLogger(DeviceController.class);

    private DeviceService deviceService;
    private SensorService sensorService;
    private CloudOrderService cloudOrderService;
    private ControlCommandService controlCommandService;

    @Autowired
    public void setSensorService(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Autowired
    public void setDeviceService(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Autowired
    public void setCloudOrderService(CloudOrderService cloudOrderService) {
        this.cloudOrderService = cloudOrderService;
    }

    @Autowired
    public void setControlCommandService(ControlCommandService controlCommandService) {
        this.controlCommandService = controlCommandService;
    }

    @PostMapping(value = "send-device-data")
    public HashMap<String, Double> receiveSensorsData(@RequestBody DeviceStateDTO deviceStateDTO) {
        logger.info("Received data " + deviceStateDTO.toString());
        HashMap<String, Double> orders = new HashMap<>();

        try {
            sensorService.updateSensorsValues(deviceStateDTO);
            orders = cloudOrderService.getDeviceOrders(deviceStateDTO.getDeviceName(),
                    deviceStateDTO.getLogin(),
                    deviceStateDTO.getPassword());
            orders.putAll(controlCommandService.getCloudLogicOrders(deviceStateDTO.getSensorsValue()));
            cloudOrderService.markOldOrdersAsRemoved(deviceStateDTO);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        logger.info(orders.toString());

        return orders;
    }

    @GetMapping(value = "{login}/connect-device")
    public void connectDevice(@PathVariable("login") String login,
                              @RequestParam("device_name") String deviceName,
                              @RequestParam("device_type") String deviceType,
                              @RequestParam("password") String password) {
        System.out.println("Board " + deviceType + " with name " + deviceName);

        try {
            deviceService.connectDevice(login, password, deviceName, deviceType);
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
