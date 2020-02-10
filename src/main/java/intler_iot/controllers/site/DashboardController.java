package intler_iot.controllers.site;

import intler_iot.controllers.entities.WidgetData;
import intler_iot.dao.entities.Widget;
import intler_iot.services.WidgetService;
import intler_iot.services.exceptions.NotAuthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
        logger.info("get Widgets List");
        String login = "admin";
        String password = "qwerty";
        List<WidgetData> widgets = null;
        try {
            widgets = widgetService.getWidgetsList(login, password);
        } catch (Throwable e) {
            logger.info(e.getMessage(), e);
        }

        return widgets;
    }

    @PostMapping("console/dashboard/update-widget")
    public void updateWidget() {

    }
}
