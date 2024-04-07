package com.kaktooth.bookstore.inventory_management.integration;

import static com.kaktooth.bookstore.inventory_management.common.TestConstants.ApiPath.ADD_BOOK;
import static com.kaktooth.bookstore.inventory_management.common.TestConstants.ApiPath.BOOK_BY_AUTHOR;
import static com.kaktooth.bookstore.inventory_management.common.TestConstants.ApiPath.BOOK_BY_GENRE;
import static com.kaktooth.bookstore.inventory_management.common.TestConstants.ApiPath.BOOK_BY_INVALID_GENRE;
import static com.kaktooth.bookstore.inventory_management.common.TestConstants.ApiPath.BOOK_BY_ISBN;
import static com.kaktooth.bookstore.inventory_management.common.TestConstants.ApiPath.BOOK_BY_TITLE;
import static com.kaktooth.bookstore.inventory_management.common.TestConstants.ApiPath.REMOVE_BOOK;
import static com.kaktooth.bookstore.inventory_management.common.TestConstants.ApiPath.UPDATE_BOOK;
import static com.kaktooth.bookstore.inventory_management.common.TestConstants.Dto.BOOK_DTO;
import static com.kaktooth.bookstore.inventory_management.common.TestConstants.Dto.INVALID_BOOK;
import static com.kaktooth.bookstore.inventory_management.common.TestConstants.Dto.UPDATED_BOOK;
import static com.kaktooth.bookstore.inventory_management.common.TestConstants.ValidationMessage.ALREADY_EXISTS_MESSAGE;
import static com.kaktooth.bookstore.inventory_management.common.TestConstants.ValidationMessage.INVALID_ARGUMENT_MESSAGE;
import static com.kaktooth.bookstore.inventory_management.common.TestConstants.ValidationMessage.NOT_FOUND_MESSAGE;
import static org.citrusframework.http.actions.HttpActionBuilder.http;
import static org.citrusframework.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.kaktooth.bookstore.inventory_management.configuration.CitrusTestConfiguration;
import com.kaktooth.bookstore.inventory_management.server.common.ServerErrorHttpStatus;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.citrusframework.TestActionRunner;
import org.citrusframework.annotations.CitrusEndpoint;
import org.citrusframework.annotations.CitrusResource;
import org.citrusframework.annotations.CitrusTest;
import org.citrusframework.config.CitrusSpringConfig;
import org.citrusframework.http.client.HttpClient;
import org.citrusframework.junit.jupiter.spring.CitrusSpringSupport;
import org.citrusframework.message.MessageType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.util.UriUtils;

@CitrusSpringSupport
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = {CitrusSpringConfig.class, CitrusTestConfiguration.class})
class BookStoreInventoryManagementIntegrationTests {

  @CitrusResource
  TestActionRunner run;

  @CitrusEndpoint
  HttpClient httpClient;

  @Autowired
  JsonMapper jsonMapper;

  @Test
  @Order(1)
  @CitrusTest
  void whenNotExistingBookIsUploadedReturnUploadedBookWithStatusOk()
      throws JsonProcessingException {
    var bookUploadJson = jsonMapper.writeValueAsString(BOOK_DTO);

    run.$(
        http()
            .client(httpClient)
            .send()
            .post(ADD_BOOK)
            .message()
            .body(bookUploadJson));

    run.$(
        http()
            .client(httpClient)
            .receive()
            .response(ServerErrorHttpStatus.OK.getHttpStatus())
            .message()
            .type(MessageType.JSON)
            .body(bookUploadJson));

  }


  @Test
  @Order(2)
  @CitrusTest
  void whenUploadingInvalidBookReturnStatusInvalidArgument() throws JsonProcessingException {
    var invalidBookJson = jsonMapper.writeValueAsString(INVALID_BOOK);

    run.$(
        http()
            .client(httpClient)
            .send()
            .post(ADD_BOOK)
            .message()
            .body(invalidBookJson));

    run.$(
        http()
            .client(httpClient)
            .receive()
            .response(ServerErrorHttpStatus.INVALID_ARGUMENT.getHttpStatus())
            .message()
            .validate(jsonPath()
                .expression("$.path", ADD_BOOK)));

  }

  @Test
  @Order(3)
  @CitrusTest
  void whenUploadingExistingBookReturnStatusAlreadyExists() throws JsonProcessingException {
    var bookUploadJson = jsonMapper.writeValueAsString(BOOK_DTO);

    run.$(
        http()
            .client(httpClient)
            .send()
            .post(ADD_BOOK)
            .message()
            .body(bookUploadJson));

    run.$(
        http()
            .client(httpClient)
            .receive()
            .response(ServerErrorHttpStatus.ALREADY_EXISTS.getHttpStatus())
            .validate(jsonPath()
                .expression("$.description", ALREADY_EXISTS_MESSAGE)
                .expression("$.url", ADD_BOOK)));

  }

  @Test
  @Order(4)
  @CitrusTest
  void whenBookIsUpdatedReturnStatusOk() throws JsonProcessingException {
    var bookUpdateJson = jsonMapper.writeValueAsString(UPDATED_BOOK);

    run.$(
        http()
            .client(httpClient)
            .send()
            .put(UPDATE_BOOK)
            .message()
            .body(bookUpdateJson));

    run.$(
        http()
            .client(httpClient)
            .receive()
            .response(ServerErrorHttpStatus.OK.getHttpStatus())
            .message()
            .body(bookUpdateJson));
  }

  @Test
  @Order(5)
  @CitrusTest
  void whenBookIsUpdatedWithInvalidBookParametersReturnStatusInvalidArgument()
      throws JsonProcessingException {
    var bookUpdateJson = jsonMapper.writeValueAsString(INVALID_BOOK);

    run.$(
        http()
            .client(httpClient)
            .send()
            .put(UPDATE_BOOK)
            .message()
            .body(bookUpdateJson));

    run.$(
        http()
            .client(httpClient)
            .receive()
            .response(ServerErrorHttpStatus.INVALID_ARGUMENT.getHttpStatus())
            .validate(jsonPath()
                .expression("$.path", UPDATE_BOOK)));

  }

  @Test
  @Order(6)
  @CitrusTest
  void retrieveExistingBookByIsbnReturnsBookWithStatusOk() throws JsonProcessingException {
    var bookJson = jsonMapper.writeValueAsString(List.of(UPDATED_BOOK));
    var encodedUrl = UriUtils.encodePath(BOOK_BY_ISBN, StandardCharsets.UTF_8.name());

    run.$(
        http()
            .client(httpClient)
            .send()
            .get(encodedUrl));

    run.$(
        http()
            .client(httpClient)
            .receive()
            .response(ServerErrorHttpStatus.OK.getHttpStatus())
            .message()
            .body(bookJson));
  }

  @Test
  @Order(7)
  @CitrusTest
  void retrieveExistingBooksByTitleReturnsBooksWithStatusOk() throws JsonProcessingException {
    var bookJson = jsonMapper.writeValueAsString(List.of(UPDATED_BOOK));
    var encodedUrl = UriUtils.encodePath(BOOK_BY_TITLE, StandardCharsets.UTF_8.name());

    run.$(
        http()
            .client(httpClient)
            .send()
            .get(encodedUrl));

    run.$(
        http()
            .client(httpClient)
            .receive()
            .response(ServerErrorHttpStatus.OK.getHttpStatus())
            .message()
            .body(bookJson));

  }

  @Test
  @Order(8)
  @CitrusTest
  void retrieveExistingBooksByAuthorReturnsBooksWithStatusOk() throws JsonProcessingException {
    var bookJson = jsonMapper.writeValueAsString(List.of(UPDATED_BOOK));
    var encodedUrl = UriUtils.encodePath(BOOK_BY_AUTHOR, StandardCharsets.UTF_8.name());

    run.$(
        http()
            .client(httpClient)
            .send()
            .get(encodedUrl));

    run.$(
        http()
            .client(httpClient)
            .receive()
            .response(ServerErrorHttpStatus.OK.getHttpStatus())
            .message()
            .body(bookJson));

  }

  @Test
  @Order(9)
  @CitrusTest
  void retrieveExistingBooksByGenreReturnsBooksWithStatusOk() throws JsonProcessingException {
    var bookJson = jsonMapper.writeValueAsString(List.of(UPDATED_BOOK));
    var encodedUrl = UriUtils.encodePath(BOOK_BY_GENRE, StandardCharsets.UTF_8.name());

    run.$(
        http()
            .client(httpClient)
            .send()
            .get(encodedUrl));

    run.$(
        http()
            .client(httpClient)
            .receive()
            .response(ServerErrorHttpStatus.OK.getHttpStatus())
            .message()
            .body(bookJson));

  }

  @Test
  @Order(10)
  @CitrusTest
  void whenRemovingExistingBookReturnRemovedBookWithStatusOk() throws JsonProcessingException {
    var bookUploadJson = jsonMapper.writeValueAsString(UPDATED_BOOK);

    run.$(
        http()
            .client(httpClient)
            .send()
            .delete(REMOVE_BOOK));

    run.$(
        http()
            .client(httpClient)
            .receive()
            .response(ServerErrorHttpStatus.OK.getHttpStatus())
            .message()
            .type(MessageType.JSON)
            .body(bookUploadJson));
  }

  @Test
  @Order(11)
  @CitrusTest
  void removeNotExistingBookReturnsStatusNotFound() {

    run.$(
        http()
            .client(httpClient)
            .send()
            .delete(REMOVE_BOOK));

    run.$(
        http()
            .client(httpClient)
            .receive()
            .response(ServerErrorHttpStatus.NOT_FOUND.getHttpStatus())
            .message()
            .type(MessageType.JSON)
            .validate(jsonPath()
                .expression("$.description", NOT_FOUND_MESSAGE)
                .expression("$.url", REMOVE_BOOK)));

  }


  @Test
  @Order(12)
  @CitrusTest
  void retrievingNonExistingBookByIsbnReturnsResponseWithStatusNotFound() {
    var encodedUrl = UriUtils.encodePath(BOOK_BY_ISBN, StandardCharsets.UTF_8.name());

    run.$(
        http()
            .client(httpClient)
            .send()
            .get(encodedUrl));

    run.$(
        http()
            .client(httpClient)
            .receive()
            .response(ServerErrorHttpStatus.NOT_FOUND.getHttpStatus())
            .validate(jsonPath()
                .expression("$.description", NOT_FOUND_MESSAGE)
                .expression("$.url", encodedUrl)));
  }

  @Test
  @Order(13)
  @CitrusTest
  void retrievingNonExistingBooksByTitleReturnsResponseWithStatusNotFound() {
    var encodedUrl = UriUtils.encodePath(BOOK_BY_TITLE, StandardCharsets.UTF_8.name());

    run.$(
        http()
            .client(httpClient)
            .send()
            .get(encodedUrl));

    run.$(
        http()
            .client(httpClient)
            .receive()
            .response(ServerErrorHttpStatus.NOT_FOUND.getHttpStatus())
            .validate(jsonPath()
                .expression("$.description", NOT_FOUND_MESSAGE)
                .expression("$.url", encodedUrl)));

  }

  @Test
  @Order(14)
  @CitrusTest
  void retrievingNonExistingBooksByAuthorReturnsBooksWithStatusNotFound() {
    var encodedUrl = UriUtils.encodePath(BOOK_BY_AUTHOR, StandardCharsets.UTF_8.name());

    run.$(
        http()
            .client(httpClient)
            .send()
            .get(encodedUrl));

    run.$(
        http()
            .client(httpClient)
            .receive()
            .response(ServerErrorHttpStatus.NOT_FOUND.getHttpStatus())
            .validate(jsonPath()
                .expression("$.description", NOT_FOUND_MESSAGE)
                .expression("$.url", encodedUrl)));

  }

  @Test
  @Order(15)
  @CitrusTest
  void retrievingNonExistingBooksByGenreReturnsResponseWithStatusNotFound() {
    var encodedUrl = UriUtils.encodePath(BOOK_BY_GENRE, StandardCharsets.UTF_8.name());

    run.$(
        http()
            .client(httpClient)
            .send()
            .get(encodedUrl));

    run.$(
        http()
            .client(httpClient)
            .receive()
            .response(ServerErrorHttpStatus.NOT_FOUND.getHttpStatus())
            .validate(jsonPath()
                .expression("$.description", NOT_FOUND_MESSAGE)
                .expression("$.url", encodedUrl)));
  }

  @Test
  @Order(16)
  @CitrusTest
  void retrievingBooksByInvalidGenreReturnsResponseWithStatusInvalidArgument() {
    var encodedUrl = UriUtils.encodePath(BOOK_BY_INVALID_GENRE, StandardCharsets.UTF_8.name());

    run.$(
        http()
            .client(httpClient)
            .send()
            .get(encodedUrl));

    run.$(
        http()
            .client(httpClient)
            .receive()
            .response(ServerErrorHttpStatus.INVALID_ARGUMENT.getHttpStatus())
            .validate(jsonPath()
                .expression("$.description", INVALID_ARGUMENT_MESSAGE)
                .expression("$.url", encodedUrl)));
  }
}
