package fr.polytech.polystore.cart.dtos;

public class CatalogProductDTO {
    public String id;
    public String name;

    public CatalogProductDTO() {
    }
    public CatalogProductDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
