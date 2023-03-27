package fr.polytech.polystore.cart.services;


import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import fr.polytech.polystore.cart.dtos.CartsDTO;
import fr.polytech.polystore.cart.dtos.CatalogProductDTO;
import fr.polytech.polystore.cart.dtos.OrderOrderDTO;
import fr.polytech.polystore.cart.dtos.ProductDTO;
import fr.polytech.polystore.cart.entities.Cart;
import fr.polytech.polystore.cart.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
@EnableDiscoveryClient
public class CartService {
    @Autowired
    CartRepository cartRepository;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Autowired
    private EurekaClient discoveryClient;

    public CartsDTO getCart() {
        Cart cart = this.cartRepository.findById("1").orElse(null);

        if (cart == null) {
            return null;
        }
        return new CartsDTO(cart);
    }

    public ProductDTO addProduct(ProductDTO createProductDTO) {
        InstanceInfo instanceInfo = discoveryClient.getNextServerFromEureka("products-service", false);
        String url = instanceInfo.getHomePageUrl() + "api/v1/products/" + createProductDTO.getProductId();
        RestTemplate restTemplate = new RestTemplate();
        CatalogProductDTO productDTO = restTemplate.getForObject(url, CatalogProductDTO.class);

        if (productDTO == null) {
            return null;
        }

        Cart cart = this.cartRepository.findById("1").orElse(null);
        if (cart == null) {
            cart = new Cart("1", new ArrayList<>());
        }

        Cart finalCart = cart;
        if (cart.getProducts() == null) {
            cart.setProducts(new ArrayList<>());
        }
        cart.getProducts()
                .stream()
                .findFirst()
                .filter(product -> product.getProductId().equals(createProductDTO.getProductId()))
                .ifPresentOrElse(product -> {
                    int finalQuantity = product.getQuantity() + createProductDTO.getQuantity();
                    if (finalQuantity > 0 ) {
                        product.setQuantity(finalQuantity);
                    } else {
                        finalCart.getProducts().remove(product);
                    }
                }, () -> finalCart.getProducts().add(new ProductDTO(createProductDTO.getProductId(), createProductDTO.getQuantity())));

        this.cartRepository.save(cart);
        return createProductDTO;
    }

    public OrderOrderDTO order() {
        Cart cart = this.cartRepository.findById("1").orElse(null);

        if (cart == null) {
            return null;
        }

        OrderOrderDTO order = new OrderOrderDTO(cart);

        InstanceInfo instanceInfo = discoveryClient.getNextServerFromEureka("order-service", false);
        String orderUrl = instanceInfo.getHomePageUrl() + "api/v1/orders/";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<OrderOrderDTO> response = restTemplate.postForEntity(orderUrl, order, OrderOrderDTO.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            this.cartRepository.delete(cart);
        }

        order = response.getBody();

        return order;
    }
}
