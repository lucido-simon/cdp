package fr.polytech.polystore.gateway.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor()
public class ProductAggregate {
    private String id;
    private String name;
    private Double price;
    private Integer quantity;

    public static ProductAggregate from(ProductDTO product, StockDTO stock) {
        return new ProductAggregate(product.id, product.name, stock.getPrice(), stock.getQuantity());
    }

    public static ProductAggregate from(ProductDTO product) {
        return new ProductAggregate(product.id, product.name, null, null);
    }

}
