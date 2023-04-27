package fr.polytech.polystore.shipping.repositories;
import fr.polytech.polystore.shipping.entities.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    Optional<Shipment> findByOrderId(String id);
    Optional<Shipment> findById(String id);
}
