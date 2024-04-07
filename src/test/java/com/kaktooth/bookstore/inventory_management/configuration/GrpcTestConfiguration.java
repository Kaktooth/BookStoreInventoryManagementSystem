package com.kaktooth.bookstore.inventory_management.configuration;

import com.kaktooth.bookstore.inventory_management.protobuf.BookStoreInventoryManagerGrpc;
import com.kaktooth.bookstore.inventory_management.server.exception.ExceptionHandler;
import com.kaktooth.bookstore.inventory_management.server.mapper.BookMapper;
import com.kaktooth.bookstore.inventory_management.server.repository.BookRepository;
import com.kaktooth.bookstore.inventory_management.server.service.BookStoreInventoryManagementService;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import java.io.IOException;
import org.junit.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@ImportAutoConfiguration(PostgresTestConfiguration.class)
@TestConfiguration
public class GrpcTestConfiguration {

  @Rule
  public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

  @Autowired
  BookRepository bookRepository;

  @Autowired
  BookMapper bookMapper;

  @Autowired
  ExceptionHandler exceptionHandler;

  @Bean
  BookStoreInventoryManagerGrpc.BookStoreInventoryManagerBlockingStub stub() throws IOException {
    var bookStoreInventoryManagementServerService =
        new BookStoreInventoryManagementService(bookRepository, bookMapper, exceptionHandler);
    final String grpcServerName = "test server";

    grpcCleanup.register(InProcessServerBuilder
        .forName(grpcServerName)
        .directExecutor()
        .addService(bookStoreInventoryManagementServerService)
        .build()
        .start());

    return BookStoreInventoryManagerGrpc.newBlockingStub(
        InProcessChannelBuilder.forName(grpcServerName).directExecutor().build());
  }
}
