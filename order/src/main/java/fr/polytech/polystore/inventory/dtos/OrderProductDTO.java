package fr.polytech.polystore.inventory.dtos;

import lombok.Data;

@Data
public class OrderProductDTO {
    private String productId;
    private Integer quantity;
}
