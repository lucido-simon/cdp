package fr.polytech.polystore.common.grpc;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.4.0)",
    comments = "Source: catalog_service.proto")
public final class CatalogServiceGrpc {

  private CatalogServiceGrpc() {}

  public static final String SERVICE_NAME = "catalog_service.CatalogService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<fr.polytech.polystore.common.grpc.GetProductsRequestGRPC,
      fr.polytech.polystore.common.grpc.GetProductsResponseGRPC> METHOD_GET_PRODUCTS =
      io.grpc.MethodDescriptor.<fr.polytech.polystore.common.grpc.GetProductsRequestGRPC, fr.polytech.polystore.common.grpc.GetProductsResponseGRPC>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "catalog_service.CatalogService", "GetProducts"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              fr.polytech.polystore.common.grpc.GetProductsRequestGRPC.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              fr.polytech.polystore.common.grpc.GetProductsResponseGRPC.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<fr.polytech.polystore.common.grpc.GetProductRequestGRPC,
      fr.polytech.polystore.common.grpc.ProductGRPC> METHOD_GET_PRODUCT =
      io.grpc.MethodDescriptor.<fr.polytech.polystore.common.grpc.GetProductRequestGRPC, fr.polytech.polystore.common.grpc.ProductGRPC>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "catalog_service.CatalogService", "GetProduct"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              fr.polytech.polystore.common.grpc.GetProductRequestGRPC.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              fr.polytech.polystore.common.grpc.ProductGRPC.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<fr.polytech.polystore.common.grpc.CreateProductGRPC,
      fr.polytech.polystore.common.grpc.ProductGRPC> METHOD_ADD_PRODUCT =
      io.grpc.MethodDescriptor.<fr.polytech.polystore.common.grpc.CreateProductGRPC, fr.polytech.polystore.common.grpc.ProductGRPC>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "catalog_service.CatalogService", "AddProduct"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              fr.polytech.polystore.common.grpc.CreateProductGRPC.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              fr.polytech.polystore.common.grpc.ProductGRPC.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CatalogServiceStub newStub(io.grpc.Channel channel) {
    return new CatalogServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CatalogServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new CatalogServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CatalogServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new CatalogServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class CatalogServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getProducts(fr.polytech.polystore.common.grpc.GetProductsRequestGRPC request,
        io.grpc.stub.StreamObserver<fr.polytech.polystore.common.grpc.GetProductsResponseGRPC> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_PRODUCTS, responseObserver);
    }

    /**
     */
    public void getProduct(fr.polytech.polystore.common.grpc.GetProductRequestGRPC request,
        io.grpc.stub.StreamObserver<fr.polytech.polystore.common.grpc.ProductGRPC> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_PRODUCT, responseObserver);
    }

    /**
     */
    public void addProduct(fr.polytech.polystore.common.grpc.CreateProductGRPC request,
        io.grpc.stub.StreamObserver<fr.polytech.polystore.common.grpc.ProductGRPC> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_ADD_PRODUCT, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GET_PRODUCTS,
            asyncUnaryCall(
              new MethodHandlers<
                fr.polytech.polystore.common.grpc.GetProductsRequestGRPC,
                fr.polytech.polystore.common.grpc.GetProductsResponseGRPC>(
                  this, METHODID_GET_PRODUCTS)))
          .addMethod(
            METHOD_GET_PRODUCT,
            asyncUnaryCall(
              new MethodHandlers<
                fr.polytech.polystore.common.grpc.GetProductRequestGRPC,
                fr.polytech.polystore.common.grpc.ProductGRPC>(
                  this, METHODID_GET_PRODUCT)))
          .addMethod(
            METHOD_ADD_PRODUCT,
            asyncUnaryCall(
              new MethodHandlers<
                fr.polytech.polystore.common.grpc.CreateProductGRPC,
                fr.polytech.polystore.common.grpc.ProductGRPC>(
                  this, METHODID_ADD_PRODUCT)))
          .build();
    }
  }

  /**
   */
  public static final class CatalogServiceStub extends io.grpc.stub.AbstractStub<CatalogServiceStub> {
    private CatalogServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CatalogServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CatalogServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CatalogServiceStub(channel, callOptions);
    }

    /**
     */
    public void getProducts(fr.polytech.polystore.common.grpc.GetProductsRequestGRPC request,
        io.grpc.stub.StreamObserver<fr.polytech.polystore.common.grpc.GetProductsResponseGRPC> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_PRODUCTS, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getProduct(fr.polytech.polystore.common.grpc.GetProductRequestGRPC request,
        io.grpc.stub.StreamObserver<fr.polytech.polystore.common.grpc.ProductGRPC> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_PRODUCT, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void addProduct(fr.polytech.polystore.common.grpc.CreateProductGRPC request,
        io.grpc.stub.StreamObserver<fr.polytech.polystore.common.grpc.ProductGRPC> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_ADD_PRODUCT, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class CatalogServiceBlockingStub extends io.grpc.stub.AbstractStub<CatalogServiceBlockingStub> {
    private CatalogServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CatalogServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CatalogServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CatalogServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public fr.polytech.polystore.common.grpc.GetProductsResponseGRPC getProducts(fr.polytech.polystore.common.grpc.GetProductsRequestGRPC request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_PRODUCTS, getCallOptions(), request);
    }

    /**
     */
    public fr.polytech.polystore.common.grpc.ProductGRPC getProduct(fr.polytech.polystore.common.grpc.GetProductRequestGRPC request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_PRODUCT, getCallOptions(), request);
    }

    /**
     */
    public fr.polytech.polystore.common.grpc.ProductGRPC addProduct(fr.polytech.polystore.common.grpc.CreateProductGRPC request) {
      return blockingUnaryCall(
          getChannel(), METHOD_ADD_PRODUCT, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class CatalogServiceFutureStub extends io.grpc.stub.AbstractStub<CatalogServiceFutureStub> {
    private CatalogServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CatalogServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CatalogServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CatalogServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<fr.polytech.polystore.common.grpc.GetProductsResponseGRPC> getProducts(
        fr.polytech.polystore.common.grpc.GetProductsRequestGRPC request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_PRODUCTS, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<fr.polytech.polystore.common.grpc.ProductGRPC> getProduct(
        fr.polytech.polystore.common.grpc.GetProductRequestGRPC request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_PRODUCT, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<fr.polytech.polystore.common.grpc.ProductGRPC> addProduct(
        fr.polytech.polystore.common.grpc.CreateProductGRPC request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_ADD_PRODUCT, getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_PRODUCTS = 0;
  private static final int METHODID_GET_PRODUCT = 1;
  private static final int METHODID_ADD_PRODUCT = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final CatalogServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(CatalogServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_PRODUCTS:
          serviceImpl.getProducts((fr.polytech.polystore.common.grpc.GetProductsRequestGRPC) request,
              (io.grpc.stub.StreamObserver<fr.polytech.polystore.common.grpc.GetProductsResponseGRPC>) responseObserver);
          break;
        case METHODID_GET_PRODUCT:
          serviceImpl.getProduct((fr.polytech.polystore.common.grpc.GetProductRequestGRPC) request,
              (io.grpc.stub.StreamObserver<fr.polytech.polystore.common.grpc.ProductGRPC>) responseObserver);
          break;
        case METHODID_ADD_PRODUCT:
          serviceImpl.addProduct((fr.polytech.polystore.common.grpc.CreateProductGRPC) request,
              (io.grpc.stub.StreamObserver<fr.polytech.polystore.common.grpc.ProductGRPC>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class CatalogServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return fr.polytech.polystore.common.grpc.CatalogServiceProto.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (CatalogServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CatalogServiceDescriptorSupplier())
              .addMethod(METHOD_GET_PRODUCTS)
              .addMethod(METHOD_GET_PRODUCT)
              .addMethod(METHOD_ADD_PRODUCT)
              .build();
        }
      }
    }
    return result;
  }
}
