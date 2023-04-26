package fr.polytech.polystore.inventory.repositories;

import fr.polytech.polystore.inventory.entities.OrderStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderStockRepository extends JpaRepository<OrderStock, Long> {
    Optional<OrderStock> findStockByOrderId(String id);
    List<OrderStock> findAllByOrderId(String id);
}
