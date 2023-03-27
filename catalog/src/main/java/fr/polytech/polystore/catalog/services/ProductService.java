package fr.polytech.polystore.catalog.services;

import fr.polytech.polystore.catalog.dtos.CreateProductDTO;
import fr.polytech.polystore.catalog.dtos.ProductDTO;
import fr.polytech.polystore.catalog.entities.Product;
import fr.polytech.polystore.catalog.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public List<ProductDTO> getProducts() {
        return productRepository.findAll().stream().map(ProductDTO::new).toList();
    }

    public ProductDTO addProduct(CreateProductDTO createProductDTO) {
        String uuid = java.util.UUID.randomUUID().toString();

        Product product = productRepository.save(new Product(uuid, createProductDTO.getName()));
        return new ProductDTO(product);
    }


    public ProductDTO getProduct(String id) {
        if (id == null) {
            return null;
        }
        return productRepository.findItemById(id).map(ProductDTO::new).orElse(null);
    }
}
