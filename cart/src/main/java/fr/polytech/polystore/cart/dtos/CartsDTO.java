package fr.polytech.polystore.cart.dtos;

import fr.polytech.polystore.cart.entities.Cart;

import java.util.List;

public class CartsDTO {
    private List<ProductDTO> products;

    public CartsDTO(List<ProductDTO> products) {
        this.products = products;
    }
    public CartsDTO(Cart cart) {
        this.products = cart.getProducts();
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void addProduct(ProductDTO productDTO) {
        this.products.add(productDTO);
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
}
