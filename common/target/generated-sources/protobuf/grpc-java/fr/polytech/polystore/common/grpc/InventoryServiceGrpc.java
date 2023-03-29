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
    comments = "Source: inventory_service.proto")
public final class InventoryServiceGrpc {

  private InventoryServiceGrpc() {}

  public static final String SERVICE_NAME = "inventory_service.InventoryService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<fr.polytech.polystore.common.grpc.StockGRPC,
      fr.polytech.polystore.common.grpc.StockGRPC> METHOD_CREATE_STOCK =
      io.grpc.MethodDescriptor.<fr.polytech.polystore.common.grpc.StockGRPC, fr.polytech.polystore.common.grpc.StockGRPC>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "inventory_service.InventoryService", "CreateStock"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              fr.polytech.polystore.common.grpc.StockGRPC.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              fr.polytech.polystore.common.grpc.StockGRPC.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<fr.polytech.polystore.common.grpc.GetStockRequestGRPC,
      fr.polytech.polystore.common.grpc.StockGRPC> METHOD_GET_STOCK =
      io.grpc.MethodDescriptor.<fr.polytech.polystore.common.grpc.GetStockRequestGRPC, fr.polytech.polystore.common.grpc.StockGRPC>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "inventory_service.InventoryService", "GetStock"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              fr.polytech.polystore.common.grpc.GetStockRequestGRPC.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              fr.polytech.polystore.common.grpc.StockGRPC.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<fr.polytech.polystore.common.grpc.GetAllStocksRequestGRPC,
      fr.polytech.polystore.common.grpc.GetAllStocksResponseGRPC> METHOD_GET_ALL_STOCKS =
      io.grpc.MethodDescriptor.<fr.polytech.polystore.common.grpc.GetAllStocksRequestGRPC, fr.polytech.polystore.common.grpc.GetAllStocksResponseGRPC>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "inventory_service.InventoryService", "GetAllStocks"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              fr.polytech.polystore.common.grpc.GetAllStocksRequestGRPC.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              fr.polytech.polystore.common.grpc.GetAllStocksResponseGRPC.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static InventoryServiceStub newStub(io.grpc.Channel channel) {
    return new InventoryServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static InventoryServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new InventoryServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static InventoryServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new InventoryServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class InventoryServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void createStock(fr.polytech.polystore.common.grpc.StockGRPC request,
        io.grpc.stub.StreamObserver<fr.polytech.polystore.common.grpc.StockGRPC> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CREATE_STOCK, responseObserver);
    }

    /**
     */
    public void getStock(fr.polytech.polystore.common.grpc.GetStockRequestGRPC request,
        io.grpc.stub.StreamObserver<fr.polytech.polystore.common.grpc.StockGRPC> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_STOCK, responseObserver);
    }

    /**
     */
    public void getAllStocks(fr.polytech.polystore.common.grpc.GetAllStocksRequestGRPC request,
        io.grpc.stub.StreamObserver<fr.polytech.polystore.common.grpc.GetAllStocksResponseGRPC> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_ALL_STOCKS, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_CREATE_STOCK,
            asyncUnaryCall(
              new MethodHandlers<
                fr.polytech.polystore.common.grpc.StockGRPC,
                fr.polytech.polystore.common.grpc.StockGRPC>(
                  this, METHODID_CREATE_STOCK)))
          .addMethod(
            METHOD_GET_STOCK,
            asyncUnaryCall(
              new MethodHandlers<
                fr.polytech.polystore.common.grpc.GetStockRequestGRPC,
                fr.polytech.polystore.common.grpc.StockGRPC>(
                  this, METHODID_GET_STOCK)))
          .addMethod(
            METHOD_GET_ALL_STOCKS,
            asyncUnaryCall(
              new MethodHandlers<
                fr.polytech.polystore.common.grpc.GetAllStocksRequestGRPC,
                fr.polytech.polystore.common.grpc.GetAllStocksResponseGRPC>(
                  this, METHODID_GET_ALL_STOCKS)))
          .build();
    }
  }

  /**
   */
  public static final class InventoryServiceStub extends io.grpc.stub.AbstractStub<InventoryServiceStub> {
    private InventoryServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private InventoryServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InventoryServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new InventoryServiceStub(channel, callOptions);
    }

    /**
     */
    public void createStock(fr.polytech.polystore.common.grpc.StockGRPC request,
        io.grpc.stub.StreamObserver<fr.polytech.polystore.common.grpc.StockGRPC> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CREATE_STOCK, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getStock(fr.polytech.polystore.common.grpc.GetStockRequestGRPC request,
        io.grpc.stub.StreamObserver<fr.polytech.polystore.common.grpc.StockGRPC> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_STOCK, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAllStocks(fr.polytech.polystore.common.grpc.GetAllStocksRequestGRPC request,
        io.grpc.stub.StreamObserver<fr.polytech.polystore.common.grpc.GetAllStocksResponseGRPC> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_ALL_STOCKS, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class InventoryServiceBlockingStub extends io.grpc.stub.AbstractStub<InventoryServiceBlockingStub> {
    private InventoryServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private InventoryServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InventoryServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new InventoryServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public fr.polytech.polystore.common.grpc.StockGRPC createStock(fr.polytech.polystore.common.grpc.StockGRPC request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CREATE_STOCK, getCallOptions(), request);
    }

    /**
     */
    public fr.polytech.polystore.common.grpc.StockGRPC getStock(fr.polytech.polystore.common.grpc.GetStockRequestGRPC request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_STOCK, getCallOptions(), request);
    }

    /**
     */
    public fr.polytech.polystore.common.grpc.GetAllStocksResponseGRPC getAllStocks(fr.polytech.polystore.common.grpc.GetAllStocksRequestGRPC request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_ALL_STOCKS, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class InventoryServiceFutureStub extends io.grpc.stub.AbstractStub<InventoryServiceFutureStub> {
    private InventoryServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private InventoryServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InventoryServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new InventoryServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<fr.polytech.polystore.common.grpc.StockGRPC> createStock(
        fr.polytech.polystore.common.grpc.StockGRPC request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CREATE_STOCK, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<fr.polytech.polystore.common.grpc.StockGRPC> getStock(
        fr.polytech.polystore.common.grpc.GetStockRequestGRPC request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_STOCK, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<fr.polytech.polystore.common.grpc.GetAllStocksResponseGRPC> getAllStocks(
        fr.polytech.polystore.common.grpc.GetAllStocksRequestGRPC request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_ALL_STOCKS, getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_STOCK = 0;
  private static final int METHODID_GET_STOCK = 1;
  private static final int METHODID_GET_ALL_STOCKS = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final InventoryServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(InventoryServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_STOCK:
          serviceImpl.createStock((fr.polytech.polystore.common.grpc.StockGRPC) request,
              (io.grpc.stub.StreamObserver<fr.polytech.polystore.common.grpc.StockGRPC>) responseObserver);
          break;
        case METHODID_GET_STOCK:
          serviceImpl.getStock((fr.polytech.polystore.common.grpc.GetStockRequestGRPC) request,
              (io.grpc.stub.StreamObserver<fr.polytech.polystore.common.grpc.StockGRPC>) responseObserver);
          break;
        case METHODID_GET_ALL_STOCKS:
          serviceImpl.getAllStocks((fr.polytech.polystore.common.grpc.GetAllStocksRequestGRPC) request,
              (io.grpc.stub.StreamObserver<fr.polytech.polystore.common.grpc.GetAllStocksResponseGRPC>) responseObserver);
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

  private static final class InventoryServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return fr.polytech.polystore.common.grpc.InventoryServiceProto.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (InventoryServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new InventoryServiceDescriptorSupplier())
              .addMethod(METHOD_CREATE_STOCK)
              .addMethod(METHOD_GET_STOCK)
              .addMethod(METHOD_GET_ALL_STOCKS)
              .build();
        }
      }
    }
    return result;
  }
}
