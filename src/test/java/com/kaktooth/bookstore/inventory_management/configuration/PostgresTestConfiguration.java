package com.kaktooth.bookstore.inventory_management.configuration;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.TestConfiguration;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@TestConfiguration
public class PostgresTestConfiguration {

  @Container
  public static PostgresContainer postgreSQLContainer = PostgresContainer.getInstance();

  @BeforeAll
  static void startDb() {
    postgreSQLContainer.start();
  }

  @AfterAll
  static void stopDb() {
    postgreSQLContainer.stop();
  }
}
