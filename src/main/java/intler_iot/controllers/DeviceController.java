package intler_iot.controllers;

import intler_iot.controllers.entities.SensorsData;
import intler_iot.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DeviceController {

    private DeviceService deviceService;

    @Autowired
    public void setDeviceService(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping(value = "send-device-data")
    public ModelAndView receiveDeviceData(@RequestBody SensorsData sensorsData, ModelAndView modelAndView) {
        System.out.println("Received data " + sensorsData.toString());
        modelAndView.setStatus(HttpStatus.OK);

        return modelAndView;
    }

    @GetMapping(value = "{login}/connect-device")
    @ResponseBody
    public void connectDevice(@PathVariable("login") String login,
                              @RequestParam("device_name") String deviceName,
                              @RequestParam("device_type") String deviceType,
                              ModelAndView modelAndView) {
        System.out.println("Board " + deviceType + " with name " + deviceName);

        deviceService.receiveDeviceData(null);

        modelAndView.setStatus(HttpStatus.OK);
    }
}
