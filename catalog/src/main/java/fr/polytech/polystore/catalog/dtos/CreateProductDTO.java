package fr.polytech.polystore.catalog.dtos;

public class CreateProductDTO {
    private String name;

    public CreateProductDTO(String name) {
        this.name = name;
    }

    public CreateProductDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
