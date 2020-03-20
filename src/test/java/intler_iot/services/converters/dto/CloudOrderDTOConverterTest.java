package intler_iot.services.converters.dto;

import intler_iot.dao.entities.CloudOrder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CloudOrderDTOConverterTest {

    private CloudOrderDTOConverter cloudOrderDTOConverter;

    private CloudOrder getCloudOrder(String name, double value) {
        CloudOrder cloudOrder = new CloudOrder();
        cloudOrder.setKeyWard(name);
        cloudOrder.setValue(value);
        return cloudOrder;
    }

    @Before
    public void init() {
        cloudOrderDTOConverter = new CloudOrderDTOConverter();
    }

    @Test
    public void convertToDTO_successConvert() {
        List<CloudOrder> cloudOrders = Arrays.asList(getCloudOrder("order1", 1),
                getCloudOrder("order2", 2),
                getCloudOrder("order3",3));
        HashMap<String, Double> ordersMap =  cloudOrderDTOConverter.convertToDTO(cloudOrders);
        assertTrue(ordersMap.containsKey("order1"));
        assertTrue(ordersMap.containsKey("order2"));
        assertTrue(ordersMap.containsKey("order3"));

    }

}
