package intler_iot.controllers.site;

import intler_iot.services.PageContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class ProfileController {

    private PageContentService pageContentService;

    @Autowired
    public void setPageContentService(PageContentService pageContentService) {
        this.pageContentService = pageContentService;
    }

    @GetMapping("/profile")
    public String getProfilePage(Model model) {
        Map<String, String> profileProperties = pageContentService.getProfilePageProperties();
        model.addAllAttributes(profileProperties);

        return "profile";
    }
}
