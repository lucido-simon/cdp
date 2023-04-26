package fr.polytech.polystore.shipping.repositories;
import fr.polytech.polystore.shipping.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findStockById(String id);
}
