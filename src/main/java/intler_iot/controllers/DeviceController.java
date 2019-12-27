package intler_iot.controllers;

import intler_iot.controllers.entities.SensorsData;
import intler_iot.dao.entities.CloudOrder;
import intler_iot.services.CloudOrderService;
import intler_iot.services.DeviceService;
import intler_iot.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class DeviceController {

    private DeviceService deviceService;
    private SensorService sensorService;
    private CloudOrderService cloudOrderService;

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

    @PostMapping(value = "send-device-data")
    public HashMap<String, Double> receiveSensorsData(@RequestBody SensorsData sensorsData) {
        System.out.println("Received data " + sensorsData.toString());
        HashMap<String, Double> orders = new HashMap<>();

        try {
            sensorService.updateSensorsValues(sensorsData);
            orders = cloudOrderService.getDeviceOrders(sensorsData.getDeviceName(),
                    sensorsData.getLogin(),
                    sensorsData.getPassword());
        } catch (Exception e) {
            System.err.println(e);
        }

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
