package fr.polytech.polystore.shipping.controllers;

import fr.polytech.polystore.common.dtos.ShipmentDTO;
import fr.polytech.polystore.shipping.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shipping")
public class ShippingController {

    @Autowired
    private ShipmentService shipmentService;

    @GetMapping()
    public ResponseEntity<List<ShipmentDTO>> getAllShipments() {
        return new ResponseEntity<>(this.shipmentService.getAllShipments(), HttpStatus.OK);
    }
}
