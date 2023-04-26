package fr.polytech.polystore.common.models;

public enum OrderStatus {
    OrderCheckout,
    OrderCreated,
    OrderPrepared,
    OrderPaid,
    OrderDelivered,
    OrderCreationFailed,
    OrderPreparationFailed,
    OrderPaymentFailed,
    OrderDeliveryFailed
}