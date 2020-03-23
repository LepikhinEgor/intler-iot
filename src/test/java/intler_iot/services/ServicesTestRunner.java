package intler_iot.services;

import intler_iot.services.converters.dto.ConvertersTestRunner;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UserServiceTest.class,
        SensorServiceTest.class,
        UserDetailsServiceTest.class,
        WidgetServiceTest.class,
        DeviceServiceTest.class,
        ControlCommandServiceTest.class,
        CloudOrderServiceTest.class,
        ConvertersTestRunner.class
})
public class ServicesTestRunner {

}
