// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: inventory_service.proto

package fr.polytech.polystore.common.grpc;

public interface GetAllStocksResponseGRPCOrBuilder extends
    // @@protoc_insertion_point(interface_extends:inventory_service.GetAllStocksResponseGRPC)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>repeated .inventory_service.StockGRPC stocks = 1;</code>
   */
  java.util.List<fr.polytech.polystore.common.grpc.StockGRPC> 
      getStocksList();
  /**
   * <code>repeated .inventory_service.StockGRPC stocks = 1;</code>
   */
  fr.polytech.polystore.common.grpc.StockGRPC getStocks(int index);
  /**
   * <code>repeated .inventory_service.StockGRPC stocks = 1;</code>
   */
  int getStocksCount();
  /**
   * <code>repeated .inventory_service.StockGRPC stocks = 1;</code>
   */
  java.util.List<? extends fr.polytech.polystore.common.grpc.StockGRPCOrBuilder> 
      getStocksOrBuilderList();
  /**
   * <code>repeated .inventory_service.StockGRPC stocks = 1;</code>
   */
  fr.polytech.polystore.common.grpc.StockGRPCOrBuilder getStocksOrBuilder(
      int index);
}