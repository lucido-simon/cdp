// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: catalog_service.proto

package fr.polytech.polystore.common.grpc;

public final class CatalogServiceProto {
  private CatalogServiceProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_catalog_service_GetProductsRequestGRPC_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_catalog_service_GetProductsRequestGRPC_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_catalog_service_GetProductsResponseGRPC_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_catalog_service_GetProductsResponseGRPC_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_catalog_service_GetProductRequestGRPC_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_catalog_service_GetProductRequestGRPC_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_catalog_service_CreateProductGRPC_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_catalog_service_CreateProductGRPC_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_catalog_service_ProductGRPC_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_catalog_service_ProductGRPC_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\025catalog_service.proto\022\017catalog_service" +
      "\"\030\n\026GetProductsRequestGRPC\"I\n\027GetProduct" +
      "sResponseGRPC\022.\n\010products\030\001 \003(\0132\034.catalo" +
      "g_service.ProductGRPC\"#\n\025GetProductReque" +
      "stGRPC\022\n\n\002id\030\001 \001(\t\"!\n\021CreateProductGRPC\022" +
      "\014\n\004name\030\001 \001(\t\"\'\n\013ProductGRPC\022\n\n\002id\030\001 \001(\t" +
      "\022\014\n\004name\030\002 \001(\t2\226\002\n\016CatalogService\022`\n\013Get" +
      "Products\022\'.catalog_service.GetProductsRe" +
      "questGRPC\032(.catalog_service.GetProductsR" +
      "esponseGRPC\022R\n\nGetProduct\022&.catalog_serv",
      "ice.GetProductRequestGRPC\032\034.catalog_serv" +
      "ice.ProductGRPC\022N\n\nAddProduct\022\".catalog_" +
      "service.CreateProductGRPC\032\034.catalog_serv" +
      "ice.ProductGRPCB:\n!fr.polytech.polystore" +
      ".common.grpcB\023CatalogServiceProtoP\001b\006pro" +
      "to3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_catalog_service_GetProductsRequestGRPC_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_catalog_service_GetProductsRequestGRPC_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_catalog_service_GetProductsRequestGRPC_descriptor,
        new java.lang.String[] { });
    internal_static_catalog_service_GetProductsResponseGRPC_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_catalog_service_GetProductsResponseGRPC_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_catalog_service_GetProductsResponseGRPC_descriptor,
        new java.lang.String[] { "Products", });
    internal_static_catalog_service_GetProductRequestGRPC_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_catalog_service_GetProductRequestGRPC_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_catalog_service_GetProductRequestGRPC_descriptor,
        new java.lang.String[] { "Id", });
    internal_static_catalog_service_CreateProductGRPC_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_catalog_service_CreateProductGRPC_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_catalog_service_CreateProductGRPC_descriptor,
        new java.lang.String[] { "Name", });
    internal_static_catalog_service_ProductGRPC_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_catalog_service_ProductGRPC_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_catalog_service_ProductGRPC_descriptor,
        new java.lang.String[] { "Id", "Name", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}