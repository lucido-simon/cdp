package fr.polytech.polystore.gateway.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockDTO {
    private String id;
    private double price;

    private int quantity;
}

