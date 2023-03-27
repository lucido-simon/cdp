package fr.polytech.polystore.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class GatewayRoutesConfig {

    @Value("${order.service.baseurl}")
    private String orderServiceBaseUrl;

    @Value("${catalog.service.baseurl}")
    private String catalogServiceBaseUrl;

    @Value("${cart.service.baseurl}")
    private String cartServiceBaseUrl;

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r
                        .path("/api/v1/orders/**")
                        .uri("lb://order-service")
                )
                .route(r -> r
                        .path("/api/v1/carts/**")
                        .uri("lb://cart-service")
                )
            .build();
    }
}
