package fr.polytech.polystore.inventory.dtos;

import fr.polytech.polystore.inventory.entities.Stock;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockDTO {
    private String id;
    private double price;

    private int quantity;

    public StockDTO(Stock stock) {
        this.id = stock.getId();
        this.price = stock.getPrice();
        this.quantity = stock.getQuantity();
    }
}

