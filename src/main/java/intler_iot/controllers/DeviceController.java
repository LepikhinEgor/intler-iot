package intler_iot.controllers;

import intler_iot.controllers.entities.SensorsData;
import intler_iot.services.DeviceService;
import intler_iot.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DeviceController {

    private DeviceService deviceService;
    private SensorService sensorService;

    @Autowired
    public void setSensorService(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Autowired
    public void setDeviceService(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping(value = "send-device-data")
    @ResponseBody
    public void receiveSensorsData(@RequestBody SensorsData sensorsData) {
        System.out.println("Received data " + sensorsData.toString());

        try {
            sensorService.receiveSensors(sensorsData);
        } catch (Exception e) {
            System.err.println(e);
        }
//        modelAndView.setStatus(HttpStatus.OK);
    }

    @GetMapping(value = "{login}/connect-device")
    @ResponseBody
    public void connectDevice(@PathVariable("login") String login,
                              @RequestParam("device_name") String deviceName,
                              @RequestParam("device_type") String deviceType,
                              @RequestParam("password") String password,
                              ModelAndView modelAndView) {
        System.out.println("Board " + deviceType + " with name " + deviceName);

        try {
            deviceService.connectDevice(login, password, deviceName, deviceType);
        } catch (Exception e) {
            System.out.println(e);
        }

        modelAndView.setStatus(HttpStatus.OK);
    }
}
