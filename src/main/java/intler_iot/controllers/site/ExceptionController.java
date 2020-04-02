package intler_iot.controllers.site;

import intler_iot.controllers.exceptions.ResourceNotFoundException;
import intler_iot.services.PageContentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@ControllerAdvice
public class ExceptionController {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    private PageContentService pageContentService;

    @Autowired
    public void setPageContentService(PageContentService pageContentService) {
        this.pageContentService = pageContentService;
    }

    //500
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public String getServerErrorPage(Model model, HttpServletRequest request, Exception e) {
        Map<String,String> pageProperties = pageContentService.get500ErrorPageProperties(request, e);
        System.out.println(pageProperties);
        model.addAllAttributes(pageProperties);

        return "errors/error500";
    }

    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public String getPageNotFoundPage(Exception e) {
//        logger.info("!!!!!!!");
//        logger.error(e.getMessage(),e);
////        Map<String,String> pageProperties = pageContentService.get500ErrorPageProperties(request, e);
////        System.out.println(pageProperties);
////        model.addAllAttributes(pageProperties);

        return "errors/error404";
    }
}
