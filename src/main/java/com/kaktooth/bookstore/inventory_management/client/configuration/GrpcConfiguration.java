package com.kaktooth.bookstore.inventory_management.client.configuration;

import com.kaktooth.bookstore.inventory_management.protobuf.BookStoreInventoryManagerGrpc;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class GrpcConfiguration {

  @Value("${grpc.client.target}")
  private String target;

  @Bean
  BookStoreInventoryManagerGrpc.BookStoreInventoryManagerStub asynchronousStub() {
    var asynchronousStubChannel =
        Grpc.newChannelBuilder(target, InsecureChannelCredentials.create())
            .build();
    return BookStoreInventoryManagerGrpc.newStub(asynchronousStubChannel);
  }

  @Bean
  BookStoreInventoryManagerGrpc.BookStoreInventoryManagerBlockingStub blockingStub() {
    var blockingStubChannel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create())
        .build();
    return BookStoreInventoryManagerGrpc.newBlockingStub(blockingStubChannel);
  }
}
