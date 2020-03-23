package intler_iot.services.converters.dto;

import intler_iot.services.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CloudOrderDTOConverterTest.class,
        SensorDTOConverterTest.class,
        WidgetDTOConverterTest.class
})
public class ConvertersTestRunner {
}
