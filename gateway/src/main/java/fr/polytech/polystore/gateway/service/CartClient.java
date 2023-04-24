package fr.polytech.polystore.gateway.service;

import fr.polytech.polystore.common.PolystoreException;
import fr.polytech.polystore.common.dtos.CartDTO;
import fr.polytech.polystore.common.dtos.CartProductDTO;
import fr.polytech.polystore.common.grpc.*;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Service
public class CartClient implements IGRPCService {

    @GrpcClient("cart_service")
    private CartServiceGRPCGrpc.CartServiceGRPCFutureStub futureStub;

    public Mono<CartDTO> getCart(String userId) {
        return createMonoFromFuture(futureStub.getCart(GetCartRequestGRPC.newBuilder().build()))
                .onErrorMap(e -> {
                    if (e.getCause() instanceof StatusRuntimeException && ((StatusRuntimeException) e.getCause()).getStatus().getCode() == io.grpc.Status.Code.NOT_FOUND) {
                        return new PolystoreException.NotFound("Cart not found");
                    }
                        return e;
                    })
                .map(this::convertFromProtoCart);
    }

    public Mono<String> order(String userId) {
        return createMonoFromFuture(futureStub.order(OrderRequestGRPC.newBuilder().build())).map(OrderResponseGRPC::getId);
    }

    // Can't return void
    public Mono<String> addProduct(CartProductDTO cartProductDTO) {
        return createMonoFromFuture(futureStub.addProduct(AddProductRequestGRPC.newBuilder().setId(cartProductDTO.getId()).setQuantity(cartProductDTO.getQuantity()).build())).map(e -> "").onErrorMap(e -> {
            if (e.getCause() instanceof StatusRuntimeException && ((StatusRuntimeException) e.getCause()).getStatus().getCode() == io.grpc.Status.Code.NOT_FOUND) {
                return new PolystoreException.NotFound("Product not found");
            }
            return e;
        });
    }


    private CartDTO convertFromProtoCart(CartGRPC cartGRPC) {
        return new CartDTO(cartGRPC.getProductsList().stream().map(productGRPC -> new CartProductDTO(productGRPC.getId(), productGRPC.getQuantity())).collect(Collectors.toList()));
    }


}
