package fr.polytech.polystore.catalog.dtos;

import fr.polytech.polystore.catalog.entities.Product;

public class ProductDTO {
    public String id;
    public String name;

    public ProductDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
    }
}
