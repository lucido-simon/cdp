package fr.polytech.polystore.common.models;

public enum OrderStatus {
    OrderCheckout,
    OrderCreated,
    OrderPrepared,
    OrderPaid,
    OrderDelivered,
    OrderPreparingFailed,
    OrderPaymentFailed,
    OrderDeliveryFailed
}