package intler_iot.services;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UserServiceTest.class,
        SensorServiceTest.class,
        UserDetailsServiceTest.class,
        WidgetServiceTest.class,
        DeviceServiceTest.class,
        ControlCommandServiceTest.class
})
public class ServicesTestRunner {

}
