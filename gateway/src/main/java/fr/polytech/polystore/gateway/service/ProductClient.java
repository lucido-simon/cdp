package fr.polytech.polystore.gateway.service;

import fr.polytech.polystore.gateway.dtos.CreateProductAggregateDTO;
import fr.polytech.polystore.gateway.dtos.ProductDTO;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductClient implements IGRPCService {

    @GrpcClient("catalog_service")
    private CatalogServiceGrpc.CatalogServiceFutureStub futureStub;

    public Mono<ProductDTO> getProduct(String productId) {
        GetProductRequestGRPC request = GetProductRequestGRPC.newBuilder().setId(productId).build();
        return createMonoFromFuture(futureStub.getProduct(request)).map(this::convertFromProtoProduct);
    }

    public Mono<List<ProductDTO>> getProducts() {
        GetProductsRequestGRPC request = GetProductsRequestGRPC.newBuilder().build();
        return createMonoFromFuture(futureStub.getProducts(request)).map(GetProductsResponseGRPC::getProductsList)
                .map(products -> products.stream().map(this::convertFromProtoProduct).collect(Collectors.toList()));
    }

    public Mono<ProductDTO> createProduct(CreateProductAggregateDTO product) {
        CreateProductGRPC request = CreateProductGRPC.newBuilder()
                .setName(product.getName())
                .build();
        return createMonoFromFuture(futureStub.addProduct(request)).map(this::convertFromProtoProduct);
    }

    private ProductDTO convertFromProtoProduct(ProductGRPC protoProduct) {
        return new ProductDTO(protoProduct.getId(), protoProduct.getName());
    }

    private ProductGRPC convertToProtoProduct(ProductDTO productDTO) {
        return ProductGRPC.newBuilder()
                .setId(productDTO.id)
                .setName(productDTO.name)
                .build();
    }
}
