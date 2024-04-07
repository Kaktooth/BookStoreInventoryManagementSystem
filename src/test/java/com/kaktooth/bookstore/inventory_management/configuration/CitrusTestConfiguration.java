package com.kaktooth.bookstore.inventory_management.configuration;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.citrusframework.http.client.HttpClient;
import org.citrusframework.http.client.HttpClientBuilder;
import org.citrusframework.validation.json.JsonTextMessageValidator;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class CitrusTestConfiguration {

  private static final String REQUEST_URL = "http://localhost:8080/";
  private static final String CONTENT_TYPE_JSON = "application/json";

  @Bean
  HttpClient httpClient() {
    return new HttpClientBuilder()
        .requestUrl(REQUEST_URL)
        .contentType(CONTENT_TYPE_JSON)
        .timeout(10000L)
        .build();
  }

  @Bean
  public JsonTextMessageValidator defaultJsonMessageValidator() {
    return new JsonTextMessageValidator();
  }

  @Bean
  JsonMapper jsonMapper() {
    return new JsonMapper();
  }
}
