package intler_iot.services;

import intler_iot.dao.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@Service
public class PageContentService {
    private static final Logger logger = LoggerFactory.getLogger(PageContentService.class);

    private UserService userService;

//    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public Map<String, String> getProfilePageProperties() {
        Map<String, String> properties = new HashMap<>();

        User user = userService.getCurrentUser();
        properties.put("login", user.getLogin());
        properties.put("email", user.getEmail());
        properties.put("serverIP", getLocalHostIP());

        return properties;
    }

    public Map<String, String> get500ErrorPageProperties(HttpServletRequest request, Exception e) {
        Map<String, String> properties = new HashMap<>();

        properties.put("url", request.getRequestURL().toString());
        properties.put("stacktrace", stackTraceToString(e));

        return properties;
    }

    private String stackTraceToString(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }


    String getLocalHostIP() {
        String serverIP;
        try {
            InetAddress addr = InetAddress.getLocalHost();
            serverIP = addr.getHostAddress();
        } catch (UnknownHostException e) {
            logger.error("Could not get local ip", e);
            serverIP = "?";
        }
        return serverIP;
    }


}
