package fr.polytech.polystore.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {

    @Value("${order.service.baseurl}")
    private String orderServiceBaseUrl;

    @Value("${payment.service.baseurl}")
    private String paymentServiceBaseUrl;

    @Value("${shipping.service.baseurl}")
    private String shippingServiceBaseUrl;

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r
                        .path("/api/v1/orders/**")
                        .uri(orderServiceBaseUrl)
                )
                .route(r -> r
                        .path("/api/v1/payments/**")
                        .uri(paymentServiceBaseUrl)
                )
                .route(r -> r
                        .path("/api/v1/shipping/**")
                        .uri(shippingServiceBaseUrl)
                )
            .build();
    }
}
