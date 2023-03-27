package fr.polytech.polystore.inventory.controllers;

import fr.polytech.polystore.inventory.dtos.StockDTO;
import fr.polytech.polystore.inventory.service.InventoryService;
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
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping()
    public ResponseEntity<StockDTO> createStock(@RequestBody StockDTO stockDTO) {
        StockDTO order = inventoryService.createStock(stockDTO);
        return new ResponseEntity<StockDTO>(order, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockDTO> getOrder(@RequestParam String id) {
        StockDTO order = this.inventoryService.getOrder(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<StockDTO>> getAllOrders() {
        List<StockDTO> orders = this.inventoryService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
