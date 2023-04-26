package fr.polytech.polystore.payment.repositories;
import fr.polytech.polystore.payment.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findStockById(String id);
}
