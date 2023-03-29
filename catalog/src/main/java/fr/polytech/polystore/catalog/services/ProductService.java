package fr.polytech.polystore.catalog.services;

import fr.polytech.polystore.common.dtos.CreateProductDTO;
import fr.polytech.polystore.common.dtos.ProductDTO;
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
        return productRepository.findAll().stream().map(this::productDTOfromProduct).toList();
    }

    public ProductDTO addProduct(CreateProductDTO createProductDTO) {
        String uuid = java.util.UUID.randomUUID().toString();

        Product product = productRepository.save(new Product(uuid, createProductDTO.getName()));
        return productDTOfromProduct(product);
    }


    public ProductDTO getProduct(String id) {
        if (id == null) {
            return null;
        }
        return productRepository.findItemById(id).map(this::productDTOfromProduct).orElse(null);
    }

    private ProductDTO productDTOfromProduct(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        return productDTO;
    }
}
