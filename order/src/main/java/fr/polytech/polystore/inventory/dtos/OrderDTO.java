package fr.polytech.polystore.inventory.dtos;

import lombok.Data;
import java.util.List;

@Data
public class OrderDTO {
    private String id;
    private String userId;
    private List<OrderProductDTO> orderProducts;
}

