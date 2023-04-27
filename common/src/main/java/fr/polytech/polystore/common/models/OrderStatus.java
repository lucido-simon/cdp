package fr.polytech.polystore.common.models;

public enum OrderStatus {
    OrderCheckout,
    OrderCreated,
    OrderPrepared,
    OrderProcessingPayment,
    OrderPaid,
    OrderDelivering,
    OrderDelivered,
    OrderCreationFailed,
    OrderPreparationFailed,
    OrderPaymentFailed,
    OrderDeliveryFailed
}