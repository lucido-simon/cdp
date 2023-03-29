package fr.polytech.polystore.gateway.service;

import fr.polytech.polystore.common.dtos.ProductDTO;
import fr.polytech.polystore.common.dtos.StockDTO;
import fr.polytech.polystore.common.dtos.CreateProductAggregateDTO;
import fr.polytech.polystore.common.dtos.ProductAggregateDTO;
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

    public Mono<ProductAggregateDTO> getProduct(String productId) {
        return Mono.zip(
                        this.productClient.getProduct(productId),
                        this.stockClient.getStock(productId)
                )
                .map(this::combine);
    }

    public Mono<List<ProductAggregateDTO>> getProducts() {
        return Mono.zip(
                        this.productClient.getProducts(),
                        this.stockClient.getStocks()
                )
                .map(this::combineList)
                .defaultIfEmpty(List.of());
    }

    private ProductAggregateDTO combine(Tuple2<ProductDTO, StockDTO> tuple) {
        return ProductAggregateDTO.from(
                tuple.getT1(),
                tuple.getT2()
        );
    }

    private List<ProductAggregateDTO> combineList(Tuple2<List<ProductDTO>, List<StockDTO>> tuple) {
        return tuple.getT1().stream().map(productDTO ->
                tuple.getT2().stream().filter(stockDTO -> stockDTO.getId().equals(productDTO.getId())).findFirst().map(stockDTO ->
                        ProductAggregateDTO.from(productDTO, stockDTO)
                ).orElseGet(() ->
                        ProductAggregateDTO.from(productDTO)
                )
        ).toList();
    }

    public Mono<ProductAggregateDTO> createProduct(CreateProductAggregateDTO product) {
        return this.productClient.createProduct(product)
                .flatMap(productDTO ->
                        {
                            StockDTO stockDTO = new StockDTO();
                            stockDTO.setId(productDTO.getId());
                            stockDTO.setQuantity(product.getQuantity());
                            stockDTO.setPrice(product.getPrice());
                            return this.stockClient.createStock(stockDTO).map(stockDTOMono ->
                                    ProductAggregateDTO.from(productDTO, stockDTOMono)
                            ).defaultIfEmpty(ProductAggregateDTO.from(productDTO));
                        }

                );
    }
}
