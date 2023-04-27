package fr.polytech.polystore.cart.services;


import fr.polytech.polystore.common.dtos.CartDTO;
import fr.polytech.polystore.cart.entities.Cart;
import fr.polytech.polystore.cart.repositories.CartRepository;
import fr.polytech.polystore.common.PolystoreException;
import fr.polytech.polystore.common.dtos.CartProductDTO;
import fr.polytech.polystore.common.dtos.OrderDTO;
import fr.polytech.polystore.common.dtos.StockDTO;
import fr.polytech.polystore.common.grpc.CatalogServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CartService {
    @GrpcClient("catalog_service")
    private CatalogServiceGrpc.CatalogServiceBlockingStub blockingStub;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartProducer cartProducer;

    public CartDTO getCart() {
        Cart cart = this.cartRepository.findById("1").orElse(null);

        if (cart == null) {
            return null;
        }
        return this.cartToCartDTO(cart);
    }

    public void addProduct(CartProductDTO createProductDTO) throws PolystoreException.NotFound {
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
                .filter(product -> product.getId().equals(createProductDTO.getId()))
                .findFirst()
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

    public String order() throws PolystoreException.Unknown {
        Cart cart = this.cartRepository.findById("1").orElse(null);

        if (cart == null) {
            return null;
        }

        OrderDTO order = this.cartToOrderDTO(cart);

        try {
            return this.cartProducer.send(order);
        } catch (Exception e) {
            throw new PolystoreException.Unknown(e.getMessage());
        }
    }

    private CartDTO cartToCartDTO(Cart cart) {
        return new CartDTO(cart.getProducts());
    }

    private OrderDTO cartToOrderDTO(Cart cart) {
        return new OrderDTO(null, "1", null, null, null, cart.getProducts().stream().map(
                product -> new StockDTO(product.getId(), null, product.getQuantity())
        ).toList());
    }
}
