package fr.polytech.polystore.cart.entities;

import fr.polytech.polystore.cart.dtos.ProductDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;
import java.util.Map;

@RedisHash("cart")
public class Cart {
    @Id
    private String userId;
    private List<ProductDTO> products;

    public Cart() {
    }

    public Cart(String userId, List<ProductDTO> products) {
        this.userId = userId;
        this.products = products;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
}