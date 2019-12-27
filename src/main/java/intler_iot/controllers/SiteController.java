package intler_iot.controllers;

import intler_iot.controllers.entities.OrderData;
import intler_iot.controllers.entities.RestResponce;
import intler_iot.services.CloudOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SiteController {

    private CloudOrderService cloudOrderService;

    @Autowired
    public void setCloudOrderService(CloudOrderService cloudOrderService) {
        this.cloudOrderService = cloudOrderService;
    }

    @PostMapping("record-cloud-order")
    public RestResponce getCloudOrder(@RequestBody OrderData orderData) {
        try {
            cloudOrderService.recordNewOrder(orderData);
        } catch (Exception e) {
            return new RestResponce(RestResponce.FAIL, e.toString());
        }
        return new RestResponce(RestResponce.SUCCESS);
    }
}
