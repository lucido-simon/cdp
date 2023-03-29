package fr.polytech.polystore.gateway.service;

import com.google.common.util.concurrent.ListenableFuture;
import fr.polytech.polystore.common.dtos.StockDTO;
import fr.polytech.polystore.common.grpc.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockClient implements IGRPCService {

    @GrpcClient("inventory_service")
    private InventoryServiceGrpc.InventoryServiceFutureStub futureStub;

    public Mono<StockDTO> getStock(String productId) {
        GetStockRequestGRPC request = GetStockRequestGRPC.newBuilder().setId(productId).build();
        ListenableFuture<StockGRPC> stockFuture = futureStub.getStock(request);
        return createMonoFromFuture(stockFuture).map(this::convertFromProtoStock);
    }

    public Mono<List<StockDTO>> getStocks() {
        GetAllStocksRequestGRPC request = GetAllStocksRequestGRPC.newBuilder().build();
        ListenableFuture<GetAllStocksResponseGRPC> response = futureStub.getAllStocks(request);
        return this.createMonoFromFuture(response).map(GetAllStocksResponseGRPC::getStocksList).map(stocks -> stocks.stream().map(this::convertFromProtoStock).collect(Collectors.toList()));
    }

    public Mono<StockDTO> createStock(StockDTO product) {
        StockGRPC request = convertToProtoStock(product);
        ListenableFuture<StockGRPC> response = futureStub.createStock(request);
        return createMonoFromFuture(response).map(this::convertFromProtoStock);
    }

    private StockDTO convertFromProtoStock(StockGRPC protoStock) {
        StockDTO stockDTO = new StockDTO();
        stockDTO.setId(protoStock.getId());
        stockDTO.setPrice(protoStock.getPrice());
        stockDTO.setQuantity(protoStock.getQuantity());
        return stockDTO;
    }

    private StockGRPC convertToProtoStock(StockDTO stockDTO) {
        return StockGRPC.newBuilder().setId(stockDTO.getId()).setPrice(stockDTO.getPrice()).setQuantity(stockDTO.getQuantity()).build();
    }
}
