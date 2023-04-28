package fr.polytech.polystore.gateway.controllers;

import fr.polytech.polystore.common.dtos.CreateProductAggregateDTO;
import fr.polytech.polystore.common.dtos.ProductAggregateDTO;
import fr.polytech.polystore.gateway.service.ProductAggregatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "*")
public class ProductAggregateController {

    @Autowired
    private ProductAggregatorService service;

    @PostMapping
    public Mono<ResponseEntity<ProductAggregateDTO>> createProduct(@RequestBody CreateProductAggregateDTO product){
        return this.service.createProduct(product)
                .map(ResponseEntity::ok);
    }

    @GetMapping("{productId}")
    public Mono<ResponseEntity<ProductAggregateDTO>> getProduct(@PathVariable String productId){
        return this.service.getProduct(productId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Mono<ResponseEntity<List<ProductAggregateDTO>>> getProducts(){
        return this.service.getProducts()
                .map(ResponseEntity::ok);
    }
}
