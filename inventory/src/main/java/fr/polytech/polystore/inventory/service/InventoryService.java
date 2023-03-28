package fr.polytech.polystore.inventory.service;


import fr.polytech.polystore.inventory.dtos.StockDTO;
import fr.polytech.polystore.inventory.entities.Stock;
import fr.polytech.polystore.inventory.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
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

    public StockDTO getStock(String id) {
        return this.stockRepository.findStockById(id).map(StockDTO::new).orElse(null);
    }

    public List<StockDTO> getAllStocks() {
        return this.stockRepository.findAll().stream().map(StockDTO::new).collect(Collectors.toList());
    }

}