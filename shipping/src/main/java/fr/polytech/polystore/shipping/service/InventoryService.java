package fr.polytech.polystore.shipping.service;


import fr.polytech.polystore.shipping.dtos.StockDTO;
import fr.polytech.polystore.shipping.entities.Stock;
import fr.polytech.polystore.shipping.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@EnableDiscoveryClient
public class InventoryService {

    @Autowired
    private StockRepository stockRepository;

    public StockDTO createStock(StockDTO stockDTO) {
        Stock stock = this.stockRepository.findStockById(stockDTO.getId()).orElse(null);
        if (stock == null) {
            stock = new Stock();
            stock.setId(stockDTO.getId());
        }

        stock.setPrice(stockDTO.getPrice());
        stock.setQuantity(stockDTO.getQuantity());

        this.stockRepository.save(stock);

        return new StockDTO(stock);
    }

    public StockDTO getOrder(String id) {
        return this.stockRepository.findStockById(id).map(StockDTO::new).orElse(null);
    }

    public List<StockDTO> getAllOrders() {
        return this.stockRepository.findAll().stream().map(StockDTO::new).collect(Collectors.toList());
    }

}