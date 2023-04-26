package fr.polytech.polystore.order.repositories;
import fr.polytech.polystore.order.entities.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    Optional<OrderProduct> findOrderProductByProductIdAndOrderId(String id, String orderId);
}
