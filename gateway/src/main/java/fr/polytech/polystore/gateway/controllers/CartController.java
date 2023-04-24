package fr.polytech.polystore.gateway.controllers;

import fr.polytech.polystore.common.PolystoreException;
import fr.polytech.polystore.common.dtos.CartDTO;
import fr.polytech.polystore.common.dtos.CartProductDTO;
import fr.polytech.polystore.gateway.service.CartClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/")
public class CartController {
    @Autowired
    private CartClient cartClient;

    @GetMapping("cart")
    public Mono<ResponseEntity<CartDTO>> getCart() {
        return cartClient.getCart("1")
                .map(ResponseEntity::ok)
                .onErrorReturn(PolystoreException.NotFound.class, ResponseEntity.notFound().build());
    }

    @PostMapping("order")
    public Mono<ResponseEntity<String>> order() {
        return cartClient.order("1").map(ResponseEntity::ok);
    }

    @PostMapping("cart")
    public Mono<ResponseEntity<String>> addProduct(@RequestBody CartProductDTO cartProductDTO) {
        return cartClient.addProduct(cartProductDTO)
                .map(ResponseEntity::ok)
                .onErrorReturn(PolystoreException.NotFound.class, ResponseEntity.notFound().build());
    }
}


