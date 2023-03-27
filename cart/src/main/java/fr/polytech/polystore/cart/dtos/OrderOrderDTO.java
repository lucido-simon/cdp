package fr.polytech.polystore.cart.dtos;

import fr.polytech.polystore.cart.entities.Cart;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OrderOrderDTO {
    public String id;
    public String userId;
    public List<ProductDTO> orderProducts;

    public OrderOrderDTO(Cart cart) {
        this.id = null;
        this.userId = cart.getUserId();
        this.orderProducts = cart.getProducts();
    }
}
