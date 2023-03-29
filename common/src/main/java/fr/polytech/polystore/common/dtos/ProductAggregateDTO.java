package fr.polytech.polystore.common.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductAggregateDTO {
    private String id;
    private String name;
    private Double price;
    private Integer quantity;

    public static ProductAggregateDTO from(ProductDTO product, StockDTO stock) {
        return new ProductAggregateDTO(product.getId(), product.getName(), stock.getPrice(), stock.getQuantity());
    }

    public static ProductAggregateDTO from(ProductDTO product) {
        return new ProductAggregateDTO(product.getId(), product.getName(), null, null);
    }

}
