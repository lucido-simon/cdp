package fr.polytech.polystore.gateway.service;

import fr.polytech.polystore.gateway.dtos.CreateProductAggregateDTO;
import fr.polytech.polystore.gateway.dtos.ProductAggregate;
import fr.polytech.polystore.gateway.dtos.ProductDTO;
import fr.polytech.polystore.gateway.dtos.StockDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductAggregatorService {

    private final ProductClient productClient;

    private final StockClient stockClient;

    public Mono<ProductAggregate> getProduct(String productId) {
        return Mono.zip(
                        this.productClient.getProduct(productId),
                        this.stockClient.getStock(productId)
                )
                .map(this::combine);
    }

    public Mono<List<ProductAggregate>> getProducts() {
        return Mono.zip(
                        this.productClient.getProducts(),
                        this.stockClient.getStocks()
                )
                .map(this::combineList)
                .defaultIfEmpty(List.of());
    }

    private ProductAggregate combine(Tuple2<ProductDTO, StockDTO> tuple) {
        return ProductAggregate.from(
                tuple.getT1(),
                tuple.getT2()
        );
    }

    private List<ProductAggregate> combineList(Tuple2<List<ProductDTO>, List<StockDTO>> tuple) {
        return tuple.getT1().stream().map(productDTO ->
                tuple.getT2().stream().filter(stockDTO -> stockDTO.getId().equals(productDTO.getId())).findFirst().map(stockDTO ->
                        ProductAggregate.from(productDTO, stockDTO)
                ).orElseGet(() ->
                        ProductAggregate.from(productDTO)
                )
        ).toList();
    }

    public Mono<ProductAggregate> createProduct(CreateProductAggregateDTO product) {
        return this.productClient.createProduct(product)
                .flatMap(productDTO ->
                            this.stockClient.createStock(new StockDTO(productDTO.getId(), product.getPrice(), product.getQuantity())
                        ).map(stockDTO ->
                                ProductAggregate.from(productDTO, stockDTO)
                        ).defaultIfEmpty(ProductAggregate.from(productDTO))

                );
    }
}
