package com.kaktooth.bookstore.inventory_management.configuration;

import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresContainer extends PostgreSQLContainer<PostgresContainer> {

  private static final String POSTGRESQL_IMAGE = "postgres:15-alpine";
  private static PostgresContainer container;

  public PostgresContainer() {
    super(POSTGRESQL_IMAGE);
  }

  public static PostgresContainer getInstance() {
    if (container == null) {
      container = new PostgresContainer();
    }
    return container;
  }

  @Override
  public void start() {
    super.start();
    System.setProperty("DB_URL", container.getJdbcUrl());
    System.setProperty("DB_USERNAME", container.getUsername());
    System.setProperty("DB_PASSWORD", container.getPassword());
  }

  @Override
  public void stop() {
    super.stop();
  }
}
