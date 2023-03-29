package fr.polytech.polystore.common.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductAggregateDTO {
    private String name;
    private double price;
    private int quantity;

}
