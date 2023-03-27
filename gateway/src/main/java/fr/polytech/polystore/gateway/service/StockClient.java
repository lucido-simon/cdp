package fr.polytech.polystore.gateway.service;

import com.netflix.discovery.EurekaClient;
import fr.polytech.polystore.gateway.dtos.CreateProductAggregateDTO;
import fr.polytech.polystore.gateway.dtos.ProductDTO;
import fr.polytech.polystore.gateway.dtos.StockDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@EnableDiscoveryClient
public class StockClient {

    private WebClient client;

    @Autowired
    private EurekaClient discoveryClient;

    public StockClient(WebClient.Builder builder) {
        this.client = builder.baseUrl("").build();
    }

    public Mono<StockDTO> getStock(String productId){
        String url = discoveryClient.getNextServerFromEureka("inventory-service", false).getHomePageUrl();
        this.client = this.client.mutate().baseUrl(url).build();
        return this.client
                .get()
                .uri("/api/v1/inventory/{productId}", productId)
                .retrieve()
                .bodyToMono(StockDTO.class)
                .onErrorResume(ex -> Mono.empty()); // switch it to empty in case of error
    }

    public Mono<List<StockDTO>> getStocks() {
        String url = discoveryClient.getNextServerFromEureka("inventory-service", false).getHomePageUrl();
        this.client = this.client.mutate().baseUrl(url).build();
        return this.client
                .get()
                .uri("/api/v1/inventory")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<StockDTO>>() {})
                .onErrorResume(ex -> Mono.empty());
    }

    public Mono<StockDTO> createStock(StockDTO product) {
        String url = discoveryClient.getNextServerFromEureka("inventory-service", false).getHomePageUrl();
        this.client = this.client.mutate().baseUrl(url).build();
        return this.client
                .post()
                .uri("/api/v1/inventory")
                .body(Mono.just(product), CreateProductAggregateDTO.class)
                .retrieve()
                .bodyToMono(StockDTO.class)
                .onErrorResume(ex -> Mono.empty());
    }
}
