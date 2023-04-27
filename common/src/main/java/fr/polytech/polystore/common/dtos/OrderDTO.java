package fr.polytech.polystore.common.dtos;

import fr.polytech.polystore.common.models.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private String id;
    private String userId;
    private String paymentId;
    private String shipmentId;
    private OrderStatus orderStatus;
    private List<StockDTO> orderProducts;
}

