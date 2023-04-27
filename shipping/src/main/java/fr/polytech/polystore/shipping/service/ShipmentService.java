package fr.polytech.polystore.shipping.service;

import fr.polytech.polystore.common.dtos.OrderDTO;
import fr.polytech.polystore.common.dtos.PaymentDTO;
import fr.polytech.polystore.common.dtos.ShipmentDTO;
import fr.polytech.polystore.common.models.OrderStatus;
import fr.polytech.polystore.common.models.PaymentStatus;
import fr.polytech.polystore.common.models.PolyStoreMessage;
import fr.polytech.polystore.common.models.ShipmentStatus;
import fr.polytech.polystore.shipping.entities.Shipment;
import fr.polytech.polystore.shipping.repositories.ShipmentRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShipmentService {

    private static final Logger logger = LoggerFactory.getLogger(ShipmentService.class);

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private ShipmentProducer shipmentProducer;

    public List<ShipmentDTO> getAllShipments() {
        return this.shipmentRepository.findAll().stream().map(this::shipmentToShipmentDTO).collect(Collectors.toList());
    }

    @Transactional
    public void createShipment(OrderDTO orderDTO) {
        logger.info("Creating shipment for order {}", orderDTO.getId());
        Shipment shipment;
        try {
            shipment = new Shipment();
            shipment.setId(java.util.UUID.randomUUID().toString());
            shipment.setOrderId(orderDTO.getId());
            shipment.setShipmentStatus(ShipmentStatus.Delivering);
            this.shipmentRepository.save(shipment);
            logger.info("Shipment created for order {}", orderDTO.getId());

        } catch (Exception e) {
            logger.error("Error while creating shipment for order {}: {}", orderDTO.getId(), e.getMessage());
            shipmentProducer.convertAndSendCompensation(orderDTO.getId(), OrderStatus.OrderDeliveryFailed);
            return;
        }

        // Simulate shipment processing
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            logger.debug(e.getMessage());
            logger.warn("Shipping processing interrupted for order {}, ignoring..", orderDTO.getId());
        }

        try {
            shipment.setShipmentStatus(ShipmentStatus.Delivered);
            this.shipmentRepository.save(shipment);
            shipmentProducer.convertAndSend(shipmentToShipmentDTO(shipment), orderDTO.getId(), OrderStatus.OrderDelivered);
        }
        catch (Exception e) {
            logger.error("Error after simulating shipping for order {}: {}", orderDTO.getId(), e.getMessage());
            this.failure(shipment);
            this.shipmentProducer.convertAndSendCompensation(orderDTO.getId(), OrderStatus.OrderDeliveryFailed);
        }
    }

    @Transactional
    public void compensateShipment(PolyStoreMessage<OrderStatus> polyStoreMessage) {
        logger.warn("Compensating shipment for order {}", polyStoreMessage.getOrderId());
        this.shipmentRepository.findByOrderId(polyStoreMessage.getOrderId()).ifPresentOrElse(
                s -> {
                    s.setShipmentStatus(ShipmentStatus.DeliveryFailed);
                    this.shipmentRepository.save(s);
                },
                () -> logger.error("Shipment not found for order {}", polyStoreMessage.getOrderId())
        );
    }

    private void failure(Shipment shipment) {
        shipment.setShipmentStatus(ShipmentStatus.DeliveryFailed);
        shipmentRepository.save(shipment);
    }

    private ShipmentDTO shipmentToShipmentDTO(Shipment shipment) {
        ShipmentDTO shipmentDTO = new ShipmentDTO();
        shipmentDTO.setId(shipment.getId());
        shipmentDTO.setOrderId(shipment.getOrderId());
        shipmentDTO.setShipmentStatus(shipment.getShipmentStatus());
        return shipmentDTO;
    }
}