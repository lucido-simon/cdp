package fr.polytech.polystore.order.models;

import fr.polytech.polystore.common.models.OrderStatus;
import fr.polytech.polystore.common.models.PolyStoreMessage;

public class PolyStoreEvent<T> extends PolyStoreMessage<T> {
    private PolyStoreEventType eventType;

    public PolyStoreEvent() {
        super();
    }

    public PolyStoreEvent(PolyStoreEventType eventType, PolyStoreMessage<T> message) {
        super(message.getOrderId(), message.getOrderStatus(), message.getPayload());
        this.eventType = eventType;
    }

    public PolyStoreEvent(String orderId, OrderStatus orderStatus, T payload, PolyStoreEventType eventType) {
        super(orderId, orderStatus, payload);
        this.eventType = eventType;
    }

    public PolyStoreEventType getEventType() {
        return eventType;
    }

    public void setEventType(PolyStoreEventType eventType) {
        this.eventType = eventType;
    }
}

