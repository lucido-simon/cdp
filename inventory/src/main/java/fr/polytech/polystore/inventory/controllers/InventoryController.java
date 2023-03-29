package fr.polytech.polystore.inventory.controllers;

import fr.polytech.polystore.common.grpc.*;
import fr.polytech.polystore.inventory.dtos.StockDTO;
import fr.polytech.polystore.inventory.service.InventoryService;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@GrpcService
public class InventoryController extends InventoryServiceGrpc.InventoryServiceImplBase {

    @Autowired
    private InventoryService inventoryService;

    @Override
    public void createStock(StockGRPC request, StreamObserver<StockGRPC> responseObserver) {
        StockDTO stockDTO = convertFromProtoStock(request);
        StockDTO createdStockDTO = inventoryService.createStock(stockDTO);

        StockGRPC createdStock = convertToProtoStock(createdStockDTO);
        responseObserver.onNext(createdStock);
        responseObserver.onCompleted();
    }

    @Override
    public void getStock(GetStockRequestGRPC request, StreamObserver<StockGRPC> responseObserver) {
        String id = request.getId();
        StockDTO stockDTO = inventoryService.getStock(id);
        if (stockDTO == null) {
            responseObserver.onError(new StatusException(Status.NOT_FOUND));
            return;
        }

        StockGRPC stock = convertToProtoStock(stockDTO);
        responseObserver.onNext(stock);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllStocks(GetAllStocksRequestGRPC request, StreamObserver<GetAllStocksResponseGRPC> responseObserver) {
        List<StockDTO> stockDTOList = inventoryService.getAllStocks();
        List<StockGRPC> stocks = stockDTOList.stream().map(this::convertToProtoStock).collect(Collectors.toList());

        GetAllStocksResponseGRPC response = GetAllStocksResponseGRPC.newBuilder().addAllStocks(stocks).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private StockDTO convertFromProtoStock(StockGRPC protoStock) {
        StockDTO stockDTO = new StockDTO();
        stockDTO.setId(protoStock.getId());
        stockDTO.setPrice(protoStock.getPrice());
        stockDTO.setQuantity(protoStock.getQuantity());
        return stockDTO;
    }

    private StockGRPC convertToProtoStock(StockDTO stockDTO) {
        return StockGRPC.newBuilder()
                .setId(stockDTO.getId())
                .setPrice(stockDTO.getPrice())
                .setQuantity(stockDTO.getQuantity())
                .build();
    }
}
