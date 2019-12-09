package intler_iot.controllers;

import intler_iot.controllers.entities.SensorsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DeviceController {

    private DeviceController deviceController;

    @Autowired
    public void setDeviceController(DeviceController deviceController) {
        this.deviceController = deviceController;
    }

    @PostMapping(value = "send-device-data")
    public ModelAndView receiveDeviceData(@RequestBody SensorsData sensorsData, ModelAndView modelAndView) {
        System.out.println("Received data " + sensorsData.toString());
        modelAndView.setStatus(HttpStatus.OK);

        return modelAndView;
    }

    @GetMapping(value = "{login}/connect-device")
    @ResponseBody
    public void connectDevice(@RequestParam("device_id") String deviceId, @PathVariable("login") String login, ModelAndView modelAndView) {
        System.out.println(deviceId + " + login " + login);

        modelAndView.setStatus(HttpStatus.OK);
    }
}
