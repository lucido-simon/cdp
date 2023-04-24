package fr.polytech.polystore.cart.controllers;

import fr.polytech.polystore.common.dtos.CartDTO;
import fr.polytech.polystore.cart.services.CartService;

import fr.polytech.polystore.common.PolystoreException;
import fr.polytech.polystore.common.dtos.CartProductDTO;
import fr.polytech.polystore.common.dtos.OrderDTO;
import fr.polytech.polystore.common.grpc.*;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@GrpcService
public class CartController extends CartServiceGRPCGrpc.CartServiceGRPCImplBase {
    @Autowired
    CartService cartService;

    @Override
    public void getCart(GetCartRequestGRPC request, StreamObserver<CartGRPC> responseObserver) {
        CartDTO cart = this.cartService.getCart();
        if (cart == null) {
            responseObserver.onError(new StatusException(Status.NOT_FOUND));
            return;
        }

        CartGRPC cartGRPC = convertToProtoCart(cart);
        responseObserver.onNext(cartGRPC);
        responseObserver.onCompleted();
    }

    @Override
    public void order(OrderRequestGRPC request, StreamObserver<OrderResponseGRPC> responseObserver) {
        OrderDTO order = this.cartService.order();
        if (order == null) {
            responseObserver.onError(new StatusException(Status.NOT_FOUND));
            return;
        }

        OrderResponseGRPC orderGRPC = OrderResponseGRPC.newBuilder().setId("5").build();
        responseObserver.onNext(orderGRPC);
        responseObserver.onCompleted();
    }

    @Override
    public void addProduct(AddProductRequestGRPC request, StreamObserver<AddProductResponseGRPC> responseObserver) {
        CartProductDTO productDTO = convertFromCreateProductRequest(request);
        try {
            this.cartService.addProduct(productDTO);
        } catch (PolystoreException.NotFound e) {
            responseObserver.onError(new StatusException(Status.NOT_FOUND));
            return;
        }

        responseObserver.onNext(AddProductResponseGRPC.newBuilder().build());
        responseObserver.onCompleted();
    }

    private CartProductDTO convertFromProtoProduct(CartProductGRPC protoProduct) {
        return new CartProductDTO(protoProduct.getId(), protoProduct.getQuantity());
    }

    private CartProductGRPC convertToProtoProduct(CartProductDTO productDTO) {
        return CartProductGRPC.newBuilder().setId(productDTO.getId()).setQuantity(productDTO.getQuantity()).build();
    }

    private CartGRPC convertToProtoCart(CartDTO cartDTO) {
        return CartGRPC.newBuilder().addAllProducts(
                cartDTO.getProducts().stream().map(this::convertToProtoProduct).collect(Collectors.toList())
        ).build();
    }

    private CartProductDTO convertFromCreateProductRequest(AddProductRequestGRPC request) {
        return new CartProductDTO(request.getId(), request.getQuantity());
    }
}
