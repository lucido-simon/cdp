package fr.polytech.polystore.catalog.repositories;

import fr.polytech.polystore.catalog.entities.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String> {
    @Query("{name:'?0'}")
    Product findItemByName(String name);

    @Query("{id:'?0'}")
    Optional<Product> findItemById(String id);
}