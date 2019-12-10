package intler_iot.services;

import intler_iot.controllers.entities.SensorsData;
import intler_iot.dao.UserDao;
import intler_iot.dao.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void receiveDeviceData(SensorsData sensorsData) {
        User user = new User();
        user.setEmail("mail228@ua.ru");
        user.setLogin("iban");
        user.setPassword("qwerty");
        userDao.create(user);
    }

}
