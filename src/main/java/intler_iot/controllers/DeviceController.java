package intler_iot.controllers;

import intler_iot.controllers.entities.DeviceData;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@Controller
public class DeviceController {
    @PostMapping(value = "")//TODO continue
    public ModelAndView receiveDeviceData(@RequestBody DeviceData deviceData, ModelAndView modelAndView) {
        System.out.println("Received data " + deviceData.toString());
        modelAndView.setStatus(HttpStatus.OK);

        return modelAndView;
    }
}
