package intler_iot.controllers.site;

import intler_iot.controllers.entities.WidgetDTO;
import intler_iot.controllers.entities.WidgetSizeDTO;
import intler_iot.dao.entities.Widget;
import intler_iot.services.WidgetService;
import intler_iot.services.converters.dto.WidgetDTOConverter;
import intler_iot.services.exceptions.NotAuthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DashboardController {

    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    private WidgetService widgetService;

    private WidgetDTOConverter widgetDTOConverter;

    @Autowired
    public void setWidgetService(WidgetService widgetService) {
        this.widgetService = widgetService;
    }

    @Autowired
    public void setWidgetDTOConverter(WidgetDTOConverter widgetDTOConverter) {
        this.widgetDTOConverter = widgetDTOConverter;
    }

    @GetMapping("/console/dashboard")
    public String getDashboardPage() {
        return "dashboard";
    }

    @GetMapping("/console/dashboard/get-widgets")
    @ResponseBody
    public List<WidgetDTO> getWidgetsList() throws NotAuthException {
        List<WidgetDTO> widgets = null;
        try {
            widgets = widgetService.getWidgetsList();
        } catch (Throwable e) {
            logger.info(e.getMessage(), e);
        }

        return widgets;
    }

    @PostMapping("console/dashboard/update-widget")
    @ResponseBody
    public void updateWidget(@RequestBody WidgetDTO widgetDTO) {
        logger.info(widgetDTO.toString());
        Widget widget = widgetDTOConverter.convertToWidget(widgetDTO);
        widgetService.updateWidget(widget);
    }

    @PostMapping("console/dashboard/create-widget")
    @ResponseBody
    public void createWidget(@RequestBody WidgetDTO widgetDTO) {
        Widget widget = widgetDTOConverter.convertToWidget(widgetDTO);
        widgetService.createWidget(widget);
    }

    @GetMapping("console/dashboard/delete-widget")
    @ResponseBody
    public void createWidget(@RequestParam("id") long id) {
        widgetService.deleteWidget(id);
    }

    @PostMapping("console/dashboard/update-widgets-size")
    @ResponseBody
    public void updateWidgetsSize(@RequestBody List<WidgetSizeDTO> widgetsSize) {
        widgetService.updateWidgetsSize(widgetsSize);
    }
}
