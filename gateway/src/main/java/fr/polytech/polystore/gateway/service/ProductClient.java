package fr.polytech.polystore.gateway.service;

import fr.polytech.polystore.gateway.dtos.CreateProductAggregateDTO;
import fr.polytech.polystore.gateway.dtos.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductClient {

    @Value("${catalog.service.baseurl}")
    private String productsServiceBaseUrl;

    private WebClient client;

    public ProductClient(WebClient.Builder builder) {
        this.client = builder.baseUrl(this.productsServiceBaseUrl).build();
    }

    public Mono<ProductDTO> getProduct(String productId) {
        return this.client
                .get()
                .uri("/api/v1/products/{productId}", productId)
                .retrieve()
                .bodyToMono(ProductDTO.class)
                .onErrorResume(ex -> Mono.empty()); // switch it to empty in case of error
    }

    public Mono<List<ProductDTO>> getProducts() {
        List<ProductDTO> test = new ArrayList<>();
        return this.client
                .get()
                .uri("/api/v1/products")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ProductDTO>>() {})
                .onErrorResume(ex -> Mono.empty());
    }

    public Mono<ProductDTO> createProduct(CreateProductAggregateDTO product) {
        return this.client
                .post()
                .uri("/api/v1/products")
                .body(Mono.just(product), CreateProductAggregateDTO.class)
                .retrieve()
                .bodyToMono(ProductDTO.class);
    }
}
