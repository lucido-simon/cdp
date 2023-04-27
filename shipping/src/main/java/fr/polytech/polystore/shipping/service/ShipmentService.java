package fr.polytech.polystore.shipping.service;

import fr.polytech.polystore.common.dtos.PaymentDTO;
import fr.polytech.polystore.common.dtos.ShipmentDTO;
import fr.polytech.polystore.shipping.entities.Shipment;
import fr.polytech.polystore.shipping.repositories.ShipmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShipmentService {
    @Autowired
    private ShipmentRepository shipmentRepository;

    public List<ShipmentDTO> getAllShipments() {
        return this.shipmentRepository.findAll().stream().map(this::shipmentToShipmentDTO).collect(Collectors.toList());
    }

    private ShipmentDTO shipmentToShipmentDTO(Shipment shipment) {
        ShipmentDTO shipmentDTO = new ShipmentDTO();
        shipmentDTO.setId(shipment.getId());
        shipmentDTO.setOrderId(shipment.getOrderId());
        shipmentDTO.setShipmentStatus(shipment.getShipmentStatus());
        return shipmentDTO;
    }
}