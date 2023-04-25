package fr.polytech.polystore.order.repositories;
import fr.polytech.polystore.order.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findProductByProductGuid(String id);
}
