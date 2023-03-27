package fr.polytech.polystore.cart.controllers;

import fr.polytech.polystore.cart.dtos.CartsDTO;
import fr.polytech.polystore.cart.dtos.OrderOrderDTO;
import fr.polytech.polystore.cart.dtos.ProductDTO;
import fr.polytech.polystore.cart.services.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
public class CartController {
    @Autowired
    CartService cartService;

    @GetMapping("")
    public ResponseEntity<CartsDTO> getProducts() {
        CartsDTO cart = this.cartService.getCart();
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cart);
    }

    @PostMapping("")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO product = this.cartService.addProduct(productDTO);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }
    @PostMapping("/order")
    public ResponseEntity<OrderOrderDTO> order() {
        OrderOrderDTO order = this.cartService.order();
        return ResponseEntity.ok(order);
    }
}
