package intler_iot.controllers;

import intler_iot.controllers.entities.OrderDTO;
import intler_iot.controllers.entities.RestResponce;
import intler_iot.services.CloudOrderService;
import intler_iot.services.WidgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SiteController {

    private CloudOrderService cloudOrderService;
    private WidgetService widgetService;

    @Autowired
    public void setCloudOrderService(CloudOrderService cloudOrderService) {
        this.cloudOrderService = cloudOrderService;
    }

    @Autowired
    public void setWidgetService(WidgetService widgetService) {
        this.widgetService = widgetService;
    }

    @PostMapping("console/dashboard/record-cloud-order")
    public RestResponce getCloudOrder(@RequestBody OrderDTO orderDTO) {
        try {
            widgetService.updateWidgetLastValue(orderDTO.getKeyword(), orderDTO.getDeviceName(), orderDTO.getValue());
            cloudOrderService.recordNewOrder(orderDTO);
        } catch (Exception e) {
            return new RestResponce(RestResponce.FAIL, e.toString());
        }
        return new RestResponce(RestResponce.SUCCESS);
    }
}
