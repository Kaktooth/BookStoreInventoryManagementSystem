package com.kaktooth.bookstore.inventory_management.server;

import com.kaktooth.bookstore.inventory_management.server.service.BookStoreInventoryManagementService;
import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookStoreInventoryManagementServer
    implements CommandLineRunner, ApplicationListener<ContextClosedEvent> {

  private final BookStoreInventoryManagementService bookStoreInventoryManagementService;

  @Value("${grpc.server.port}")
  private int port;
  private Server server;

  @Override
  public void run(String... args) {
    try {
      this.start();
      log.info("gRPC server started successfully");
    } catch (Exception ex) {
      log.info("Cant start the server: " + ex.getMessage());
    }
  }

  @Override
  public void onApplicationEvent(ContextClosedEvent event) {
    try {
      this.stop();
    } catch (InterruptedException ex) {
      log.info("Exception when stopping server is encountered: " + ex.getMessage());
    }
  }

  public void start() throws IOException {
    var serverBuilder = Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create());
    server = serverBuilder
        .addService(bookStoreInventoryManagementService)
        .build();
    server.start();
    Runtime.getRuntime().addShutdownHook(
        new Thread(() -> {
          log.warn(" --- Shutting down gRPC server since JVM is shutting down.");
          try {
            BookStoreInventoryManagementServer.this.stop();
          } catch (InterruptedException ex) {
            log.error(" Stopping gRPC server is interrupted: " + ex.getMessage());
          }
          log.warn(" --- Server shut down.");
        })
    );
  }

  public void stop() throws InterruptedException {
    if (server != null) {
      server.awaitTermination(5, TimeUnit.SECONDS);
      server.shutdown();
    }
  }
}
