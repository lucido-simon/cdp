package fr.polytech.polystore.catalog.controllers;

import fr.polytech.polystore.catalog.dtos.CreateProductDTO;
import fr.polytech.polystore.catalog.dtos.ProductDTO;
import fr.polytech.polystore.catalog.entities.Product;
import fr.polytech.polystore.catalog.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    private Environment env;

    @GetMapping("")
    public List<ProductDTO> getProducts() {
        System.out.println("Catalog service port: " + env.getProperty("local.server.port"));
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public ProductDTO getProduct(@PathVariable String id) {
        return productService.getProduct(id);
    }

    @PostMapping("")
    public Product addProduct(@RequestBody CreateProductDTO createProductDTO) {
        return this.productService.addProduct(createProductDTO);
    }

}
