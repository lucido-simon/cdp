package fr.polytech.polystore.gateway.service;

import fr.polytech.polystore.gateway.dtos.CreateProductAggregateDTO;
import fr.polytech.polystore.gateway.dtos.StockDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


import java.util.List;

@Service
public class StockClient {
    @Value("${inventory.service.baseurl}")
    private String inventoryServiceBaseUrl;

    private final WebClient client;


    public StockClient(WebClient.Builder builder) {
        this.client = builder.baseUrl(this.inventoryServiceBaseUrl).build();
    }

    public Mono<StockDTO> getStock(String productId){
        return this.client
                .get()
                .uri("/api/v1/inventory/{productId}", productId)
                .retrieve()
                .bodyToMono(StockDTO.class)
                .onErrorResume(ex -> Mono.empty()); // switch it to empty in case of error
    }

    public Mono<List<StockDTO>> getStocks() {
        return this.client
                .get()
                .uri("/api/v1/inventory")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<StockDTO>>() {})
                .onErrorResume(ex -> Mono.empty());
    }

    public Mono<StockDTO> createStock(StockDTO product) {
        return this.client
                .post()
                .uri("/api/v1/inventory")
                .body(Mono.just(product), CreateProductAggregateDTO.class)
                .retrieve()
                .bodyToMono(StockDTO.class)
                .onErrorResume(ex -> Mono.empty());
    }
}
