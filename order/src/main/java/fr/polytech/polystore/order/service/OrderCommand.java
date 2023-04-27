package fr.polytech.polystore.order.service;


import fr.polytech.polystore.common.dtos.OrderDTO;
import fr.polytech.polystore.common.dtos.PaymentDTO;
import fr.polytech.polystore.common.dtos.ShipmentDTO;
import fr.polytech.polystore.common.dtos.StockDTO;
import fr.polytech.polystore.common.models.OrderStatus;
import fr.polytech.polystore.common.models.PolyStoreMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderCommand {
    private static final Logger logger = LoggerFactory.getLogger(OrderCommand.class);
    @Autowired
    private OrderProducer orderProducer;

    public String createOrder(PolyStoreMessage<OrderDTO> event) {
        String id = java.util.UUID.randomUUID().toString();
        event.setOrderId(id);
        event.setOrderStatus(OrderStatus.OrderCreated);
        OrderDTO orderDTO = event.getPayload();
        orderDTO.setId(id);
        logger.info("Creating order {}", orderDTO.getId());

        // TODO: Send event
        PolyStoreMessage<List<StockDTO>> stockEvent = new PolyStoreMessage<>();
        stockEvent.setOrderId(id);
        stockEvent.setOrderStatus(OrderStatus.OrderCreated);
        stockEvent.setPayload(orderDTO.getOrderProducts());

        orderProducer.convertAndSendInventory(stockEvent);

        return orderDTO.getId();
    }

    public void updateProducts(PolyStoreMessage<List<StockDTO>> event) {
        logger.info("Inventory event for order: " + event.getOrderId());
        event.setOrderStatus(OrderStatus.OrderPrepared);
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(event.getOrderId());
        orderDTO.setOrderProducts(event.getPayload());

        // TODO: Send event

        orderProducer.convertAndSendPayment(orderDTO);
    }

    public void updatePayment(PolyStoreMessage<PaymentDTO> message) {
        logger.info("Payment event for order: " + message.getOrderId());
        // TODO: Send event
        if (message.getOrderStatus() == OrderStatus.OrderPaid) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setId(message.getOrderId());
            orderDTO.setOrderStatus(message.getOrderStatus());
            this.orderProducer.convertAndSendShipping(orderDTO);
        }
    }

    public void shppingUpdate(PolyStoreMessage<ShipmentDTO> message) {
        logger.info("Shipping update for order: " + message.getOrderId());
        // TODO: Send event

        if (message.getOrderStatus() == OrderStatus.OrderDelivered) {
            logger.info("Order delivered: " + message.getOrderId());
        }
    }

    public void shippingCompensate(PolyStoreMessage<OrderStatus> message) {
    }

    public void paymentCompensate(PolyStoreMessage<OrderStatus> message) {
    }

    public void inventoryCompensate(PolyStoreMessage<OrderStatus> message) {
    }
}
