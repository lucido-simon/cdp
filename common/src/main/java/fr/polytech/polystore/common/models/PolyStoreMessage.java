package fr.polytech.polystore.common.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolyStoreMessage<T> {
    private String orderId;
    private OrderStatus orderStatus;
    private T payload;
}
