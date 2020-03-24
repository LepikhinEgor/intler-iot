package intler_iot.services.converters.dto;

import intler_iot.controllers.entities.OrderDTO;
import intler_iot.dao.entities.CloudOrder;
import intler_iot.dao.entities.Device;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@Service
public class CloudOrderDTOConverter {

    public CloudOrder convertToDomain(OrderDTO orderDTO, Device device) {
        CloudOrder order = new CloudOrder();
        order.setKeyWard(orderDTO.getKeyword());
        order.setValue(orderDTO.getValue());
        order.setDevice(device);
        order.setTiming(new Timestamp(System.currentTimeMillis()));
        order.setUsed(false);
        order.setRemoved(false);

        return order;
    }

    public HashMap<String, Double> convertToDTO(List<CloudOrder> ordersList) {
        HashMap<String, Double> ordersMap = new HashMap<>();
        for (CloudOrder order: ordersList) {
            ordersMap.put(order.getKeyWard(), order.getValue());
        }

        return ordersMap;
    }

}
