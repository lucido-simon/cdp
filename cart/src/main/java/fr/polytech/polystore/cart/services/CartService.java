package fr.polytech.polystore.cart.services;


import fr.polytech.polystore.common.dtos.CartDTO;
import fr.polytech.polystore.cart.entities.Cart;
import fr.polytech.polystore.cart.repositories.CartRepository;
import fr.polytech.polystore.common.PolystoreException;
import fr.polytech.polystore.common.dtos.CartProductDTO;
import fr.polytech.polystore.common.dtos.OrderDTO;
import fr.polytech.polystore.common.grpc.CatalogServiceGrpc;
import fr.polytech.polystore.common.grpc.GetProductRequestGRPC;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class CartService {
    @GrpcClient("catalog_service")
    private CatalogServiceGrpc.CatalogServiceBlockingStub blockingStub;

    @Autowired
    CartRepository cartRepository;

    public CartDTO getCart() {
        Cart cart = this.cartRepository.findById("1").orElse(null);

        if (cart == null) {
            return null;
        }
        return this.CartToCartDTO(cart);
    }

    public void addProduct(CartProductDTO createProductDTO) throws PolystoreException.NotFound {
        try {
            this.blockingStub.getProduct(GetProductRequestGRPC.newBuilder().setId(createProductDTO.getId()).build());
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode().equals(Status.NOT_FOUND.getCode())) {
                throw new PolystoreException.NotFound("Product not found");
            }
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
                .filter(product -> product.getId().equals(createProductDTO.getId()))
                .ifPresentOrElse(product -> {
                    int finalQuantity = product.getQuantity() + createProductDTO.getQuantity();
                    if (finalQuantity > 0 ) {
                        product.setQuantity(finalQuantity);
                    } else {
                        finalCart.getProducts().remove(product);
                    }
                }, () -> finalCart.getProducts().add(new CartProductDTO(createProductDTO.getId(), createProductDTO.getQuantity())));

        this.cartRepository.save(cart);
    }

    public OrderDTO order() {
        Cart cart = this.cartRepository.findById("1").orElse(null);

        if (cart == null) {
            return null;
        }

        OrderDTO order = this.CartToOrderDTO(cart);

        return order;
    }

    private CartDTO CartToCartDTO(Cart cart) {
        return new CartDTO(cart.getProducts());
    }

    private OrderDTO CartToOrderDTO(Cart cart) {
        return new OrderDTO("1", "1" , cart.getProducts());
    }
}
