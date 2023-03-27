package fr.polytech.polystore.gateway.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor()
public class CreateProductAggregateDTO {
    private String name;
    private double price;
    private int quantity;

}
