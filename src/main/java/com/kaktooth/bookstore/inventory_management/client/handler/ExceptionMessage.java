package com.kaktooth.bookstore.inventory_management.client.handler;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:message.properties")
@Getter
public class ExceptionMessage {

  @Value("${bad.request.message}")
  private String badRequestMessage;

  @Value("${not.found.message}")
  private String notFoundMessage;

  @Value("${internal.error.message}")
  private String internalServerErrorMessage;
}
