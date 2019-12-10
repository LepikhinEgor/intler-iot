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

    public void connectDevice(String deviceName, String deviceType) {
        //TODO continue
    }
}
