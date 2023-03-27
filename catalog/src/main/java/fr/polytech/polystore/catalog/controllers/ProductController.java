package fr.polytech.polystore.catalog.controllers;

import fr.polytech.polystore.catalog.*;
import fr.polytech.polystore.catalog.CatalogServiceGrpc.CatalogServiceImplBase;
import fr.polytech.polystore.catalog.dtos.CreateProductDTO;
import fr.polytech.polystore.catalog.dtos.ProductDTO;
import fr.polytech.polystore.catalog.services.ProductService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@GrpcService
public class ProductController extends CatalogServiceImplBase {

    @Autowired
    ProductService productService;

    @Override
    public void getProducts(GetProductsRequestGRPC request, StreamObserver<GetProductsResponseGRPC> responseObserver) {
        List<ProductDTO> productDTOList = productService.getProducts();
        List<ProductGRPC> products = productDTOList.stream().map(this::convertToProtoProduct).toList();

        GetProductsResponseGRPC response = GetProductsResponseGRPC.newBuilder().addAllProducts((Iterable<? extends ProductGRPC>) products.iterator()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getProduct(GetProductRequestGRPC request, StreamObserver<ProductGRPC> responseObserver) {
        String id = request.getId();
        ProductDTO productDTO = productService.getProduct(id);

        ProductGRPC product = convertToProtoProduct(productDTO);
        responseObserver.onNext(product);
        responseObserver.onCompleted();
    }

    @Override
    public void addProduct(CreateProductGRPC request, StreamObserver<ProductGRPC> responseObserver) {
        CreateProductDTO createProductDTO = new CreateProductDTO(request.getName());
        ProductDTO productDTO = productService.addProduct(createProductDTO);

        ProductGRPC product = convertToProtoProduct(productDTO);
        responseObserver.onNext(product);
        responseObserver.onCompleted();
    }

    private ProductGRPC convertToProtoProduct(ProductDTO productDTO) {
        return ProductGRPC.newBuilder().setId(productDTO.id).setName(productDTO.name).build();
    }
}
