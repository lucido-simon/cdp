package fr.polytech.polystore.common.models;

public enum OrderStatus {
    OrderCheckout,
    OrderCreated,
    OrderPrepared,
    OrderPaid,
    OrderDelivered,
    OrderCreationFailed,
    OrderPreparingFailed,
    OrderPaymentFailed,
    OrderDeliveryFailed
}