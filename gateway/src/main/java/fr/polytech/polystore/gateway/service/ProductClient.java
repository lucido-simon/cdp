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
public class ProductClient {

    private WebClient client;

    @Autowired
    private EurekaClient discoveryClient;

    public ProductClient(WebClient.Builder builder) {
        this.client = builder.baseUrl("").build();
    }

    public Mono<ProductDTO> getProduct(String productId) {
        String url = discoveryClient.getNextServerFromEureka("products-service", false).getHomePageUrl();
        this.client = this.client.mutate().baseUrl(url).build();
        return this.client
                .get()
                .uri("/api/v1/products/{productId}", productId)
                .retrieve()
                .bodyToMono(ProductDTO.class)
                .onErrorResume(ex -> Mono.empty()); // switch it to empty in case of error
    }

    public Mono<List<ProductDTO>> getProducts() {
        String url = discoveryClient.getNextServerFromEureka("products-service", false).getHomePageUrl();
        this.client = this.client.mutate().baseUrl(url).build();
        List<ProductDTO> test = new ArrayList<>();
        return this.client
                .get()
                .uri("/api/v1/products")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ProductDTO>>() {})
                .onErrorResume(ex -> Mono.empty());
    }

    public Mono<ProductDTO> createProduct(CreateProductAggregateDTO product) {
        String url = discoveryClient.getNextServerFromEureka("products-service", false).getHomePageUrl();
        this.client = this.client.mutate().baseUrl(url).build();
        return this.client
                .post()
                .uri("/api/v1/products")
                .body(Mono.just(product), CreateProductAggregateDTO.class)
                .retrieve()
                .bodyToMono(ProductDTO.class);
    }
}
