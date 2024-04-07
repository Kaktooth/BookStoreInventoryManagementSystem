package com.kaktooth.bookstore.inventory_management.integration;

import com.kaktooth.bookstore.inventory_management.BookStoreInventoryManagementApplication;
import com.kaktooth.bookstore.inventory_management.configuration.PostgresContainer;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextClosedEvent;

public class TestBookStoreInventoryManagementApplication implements CommandLineRunner,
    ApplicationListener<ContextClosedEvent> {


  public static void main(String[] args) {
    SpringApplication.from(BookStoreInventoryManagementApplication::main).run(args);
  }

  @Override
  public void run(String... args) {
    PostgresContainer.getInstance().start();
  }

  @Override
  public void onApplicationEvent(@NotNull ContextClosedEvent event) {
    PostgresContainer.getInstance().stop();
  }

  @Bean
  ServletWebServerFactory servletWebServerFactory() {
    return new TomcatServletWebServerFactory();
  }
}
