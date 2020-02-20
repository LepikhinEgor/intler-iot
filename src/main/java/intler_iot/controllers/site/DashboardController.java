package intler_iot.controllers.site;

import intler_iot.controllers.entities.WidgetData;
import intler_iot.controllers.entities.WidgetSize;
import intler_iot.dao.entities.Widget;
import intler_iot.services.WidgetService;
import intler_iot.services.exceptions.NotAuthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class DashboardController {

    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    private WidgetService widgetService;

    @Autowired
    public void setWidgetService(WidgetService widgetService) {
        this.widgetService = widgetService;
    }

    @GetMapping("/console/dashboard")
    public String getDashboardPage() {
        return "dashboard";
    }

    @GetMapping("/console/dashboard/get-widgets")
    @ResponseBody
    public List<WidgetData> getWidgetsList() throws NotAuthException {
        List<WidgetData> widgets = null;
        try {
            widgets = widgetService.getWidgetsList();
        } catch (Throwable e) {
            logger.info(e.getMessage(), e);
        }

        return widgets;
    }

    @PostMapping("console/dashboard/update-widget")
    @ResponseBody
    public void updateWidget(@RequestBody Widget widget) {
        logger.info(widget.toString());
        widgetService.updateWidget(widget);
    }

    @PostMapping("console/dashboard/create-widget")
    @ResponseBody
    public void createWidget(@RequestBody Widget widget) {
        widgetService.createWidget(widget);
    }

    @PostMapping("console/dashboard/update-widgets-size")
    @ResponseBody
    public void updateWidgetsSize(@RequestBody List<WidgetSize> widgetsSize) {
        widgetService.updateWidgetsSize(widgetsSize);
    }
}
