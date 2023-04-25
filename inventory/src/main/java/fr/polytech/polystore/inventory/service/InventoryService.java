package fr.polytech.polystore.inventory.service;


import fr.polytech.polystore.common.PolystoreException;
import fr.polytech.polystore.common.dtos.StockDTO;
import fr.polytech.polystore.common.models.PolyStoreMessage;
import fr.polytech.polystore.inventory.entities.OrderStock;
import fr.polytech.polystore.inventory.entities.Stock;
import fr.polytech.polystore.inventory.repositories.OrderStockRepository;
import fr.polytech.polystore.inventory.repositories.StockRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private OrderStockRepository orderStockRepository;

    @Autowired
    private InventoryProducer inventoryProducer;

    public StockDTO createStock(StockDTO stockDTO) {
        Stock stock = this.stockRepository.findStockById(stockDTO.getId()).orElse(null);
        if (stock == null) {
            stock = new Stock();
            stock.setId(stockDTO.getId());
        }

        stock.setPrice(stockDTO.getPrice());
        stock.setQuantity(stockDTO.getQuantity());

        this.stockRepository.save(stock);

        return this.stockDTOfromStock(stock);
    }

    public StockDTO getStock(String id) {
        return this.stockRepository.findStockById(id).map(this::stockDTOfromStock).orElse(null);
    }

    public List<StockDTO> getAllStocks() {
        return this.stockRepository.findAll().stream().map(this::stockDTOfromStock).collect(Collectors.toList());
    }

    @Transactional
    public List<StockDTO> removeStockFromOrder(PolyStoreMessage<List<StockDTO>> order) {
        List<StockDTO> stocks = order.getPayload();

        try {
            for (StockDTO stock : stocks) {
                Stock stockEntity = this.stockRepository.findStockById(stock.getId()).orElseThrow(() -> new PolystoreException.NotFound("Stock not found"));
                stockEntity.setQuantity(stockEntity.getQuantity() - stock.getQuantity());
                stock.setPrice(stockEntity.getPrice());

                OrderStock orderStock = new OrderStock();
                orderStock.setProductId(stockEntity.getId());
                orderStock.setOrderId(order.getOrderId());
                orderStock.setStock(stockEntity);
                orderStock.setQuantity(stock.getQuantity());
                orderStock.setBuyPrice(stockEntity.getPrice());
                orderStockRepository.save(orderStock);
            }

            this.inventoryProducer.send(stocks, order.getOrderId());
        } catch (PolystoreException.NotFound e) {
            this.failure(order);
        }
        return stocks;
    }

    private void failure(PolyStoreMessage<List<StockDTO>> order) {
        // TODO
        throw new RuntimeException("Not implemented");
    }

    private StockDTO stockDTOfromStock(Stock stock) {
        StockDTO stockDTO = new StockDTO();
        stockDTO.setId(stock.getId());
        stockDTO.setPrice(stock.getPrice());
        stockDTO.setQuantity(stock.getQuantity());
        return stockDTO;
    }

}