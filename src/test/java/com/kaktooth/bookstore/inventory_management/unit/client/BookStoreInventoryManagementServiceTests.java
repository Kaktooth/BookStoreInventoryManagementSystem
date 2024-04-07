package com.kaktooth.bookstore.inventory_management.unit.client;

import static com.kaktooth.bookstore.inventory_management.common.TestConstants.Dto.BOOK_DTO;
import static com.kaktooth.bookstore.inventory_management.common.TestConstants.Dto.BOOK_WITH_NON_EXISTENT_ISBN;
import static com.kaktooth.bookstore.inventory_management.common.TestConstants.Dto.INVALID_BOOK;
import static com.kaktooth.bookstore.inventory_management.common.TestConstants.Dto.UPDATED_BOOK;
import static com.kaktooth.bookstore.inventory_management.common.TestConstants.Request.RETRIEVE_BY_AUTHOR;
import static com.kaktooth.bookstore.inventory_management.common.TestConstants.Request.RETRIEVE_BY_GENRE;
import static com.kaktooth.bookstore.inventory_management.common.TestConstants.Request.RETRIEVE_BY_INVALID_GENRE;
import static com.kaktooth.bookstore.inventory_management.common.TestConstants.Request.RETRIEVE_BY_ISBN;
import static com.kaktooth.bookstore.inventory_management.common.TestConstants.Request.RETRIEVE_BY_NON_EXISTENT_AUTHOR;
import static com.kaktooth.bookstore.inventory_management.common.TestConstants.Request.RETRIEVE_BY_NON_EXISTENT_GENRE;
import static com.kaktooth.bookstore.inventory_management.common.TestConstants.Request.RETRIEVE_BY_NON_EXISTENT_ISBN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.kaktooth.bookstore.inventory_management.client.service.BookStoreInventoryManagementServiceClient;
import com.kaktooth.bookstore.inventory_management.configuration.GrpcTestConfiguration;
import com.kaktooth.bookstore.inventory_management.protobuf.BookRetrieveOption;
import com.kaktooth.bookstore.inventory_management.protobuf.RetrieveRequest;
import com.kaktooth.bookstore.inventory_management.server.exception.ExceptionHandler;
import com.kaktooth.bookstore.inventory_management.server.mapper.BookMapper;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = GrpcTestConfiguration.class)
class BookStoreInventoryManagementServiceTests {

  @Autowired
  BookStoreInventoryManagementServiceClient bookStoreInventoryManagementService;

  @Autowired
  ExceptionHandler exceptionHandler;

  @Autowired
  BookMapper bookMapper;

  @Test
  @Order(1)
  void whenBookAddedWithSameIsbnShouldThrowException() {
    var uploadedBook = bookStoreInventoryManagementService.addBook(BOOK_DTO);
    assertThat(uploadedBook).isNotNull();
    assertThat(uploadedBook.getTitle()).isEqualTo(BOOK_DTO.getTitle());
    assertThat(uploadedBook.getAuthor()).isEqualTo(BOOK_DTO.getAuthor());
    assertThat(uploadedBook.getIsbn()).isEqualTo(BOOK_DTO.getIsbn());
    assertThat(uploadedBook.getGenre()).isEqualTo(BOOK_DTO.getGenre());

    assertThatThrownBy(
        () -> bookStoreInventoryManagementService.addBook(BOOK_DTO)).isInstanceOf(
            StatusRuntimeException.class)
        .hasMessage(exceptionHandler.getBookAlreadyExistsException(
            bookMapper.mapToProto(BOOK_DTO)).getMessage());
  }

  @Test
  @Order(2)
  void whenBookForUpdateIsValidReturnsUpdatedBook() {
    var newlyUpdatedBook = bookStoreInventoryManagementService.updateBook(UPDATED_BOOK);
    assertThat(newlyUpdatedBook).isNotNull();
    assertThat(newlyUpdatedBook.getTitle()).isEqualTo(UPDATED_BOOK.getTitle());
    assertThat(newlyUpdatedBook.getAuthor()).isEqualTo(UPDATED_BOOK.getAuthor());
    assertThat(newlyUpdatedBook.getGenre()).isEqualTo(UPDATED_BOOK.getGenre());

    assertThat(UPDATED_BOOK.getIsbn()).isEqualTo(UPDATED_BOOK.getIsbn());
  }

  @Test
  @Order(3)
  void whenUploadedBookIsNotFoundByIsbnForUpdateThrowException() {
    final var bookUpdateRetrieveRequest = RetrieveRequest.newBuilder()
        .setBookRetrieveOption(BookRetrieveOption.BY_ISBN)
        .setRequestValue(BOOK_WITH_NON_EXISTENT_ISBN.getIsbn())
        .build();

    assertThatThrownBy(() -> bookStoreInventoryManagementService.updateBook(
        BOOK_WITH_NON_EXISTENT_ISBN)).isInstanceOf(StatusRuntimeException.class)
        .hasMessage(
            exceptionHandler.getBookNotFoundException(bookUpdateRetrieveRequest).getMessage());
  }

  @Test
  @Order(4)
  void whenBookParamsForUpdateIsInvalidThrowException() {
    assertThatThrownBy(
        () -> bookStoreInventoryManagementService.updateBook(INVALID_BOOK)).isInstanceOf(
            StatusRuntimeException.class)
        .hasMessage(exceptionHandler.getInvalidBookParametersException(
            bookMapper.mapToProto(INVALID_BOOK)).getMessage());
  }

  @Test
  @Order(5)
  void whenRetrievingBookByValidIsbnReturnBook() {
    var retrievedBook = bookStoreInventoryManagementService.retrieveBooks(RETRIEVE_BY_ISBN);
    assertTrue(retrievedBook.iterator().hasNext());
    assertEquals(retrievedBook.iterator().next().getIsbn(), UPDATED_BOOK.getIsbn());
  }

  @Test
  @Order(6)
  void whenRetrievingBookByNotExistingIsbnThrowException() {
    assertThatThrownBy(() -> bookStoreInventoryManagementService.retrieveBooks(
        RETRIEVE_BY_NON_EXISTENT_ISBN)).isInstanceOf(StatusRuntimeException.class)
        .hasMessage(
            exceptionHandler.getBookNotFoundException(RETRIEVE_BY_NON_EXISTENT_ISBN).getMessage());
  }

  @Test
  @Order(7)
  void whenRetrievingBookByValidAuthorReturnBooks() {
    var retrievedBook = bookStoreInventoryManagementService.retrieveBooks(RETRIEVE_BY_AUTHOR);
    assertTrue(retrievedBook.iterator().hasNext());
    assertEquals(retrievedBook.iterator().next().getAuthor(), UPDATED_BOOK.getAuthor());
  }

  @Test
  @Order(8)
  void whenRetrievingBookByNotExistingAuthorThrowException() {
    assertThatThrownBy(() -> bookStoreInventoryManagementService.retrieveBooks(
        RETRIEVE_BY_NON_EXISTENT_AUTHOR)).isInstanceOf(StatusRuntimeException.class)
        .hasMessage(exceptionHandler.getBookNotFoundException(RETRIEVE_BY_NON_EXISTENT_AUTHOR)
            .getMessage());
  }

  @Test
  @Order(9)
  void whenRetrievingBookByValidGenreReturnBooks() {
    var retrievedBook = bookStoreInventoryManagementService.retrieveBooks(RETRIEVE_BY_GENRE);
    assertTrue(retrievedBook.iterator().hasNext());
    assertEquals(retrievedBook.iterator().next().getGenre(), UPDATED_BOOK.getGenre());
  }

  @Test
  @Order(10)
  void whenRetrievingBookByNotExistingGenreThrowException() {
    assertThatThrownBy(() -> bookStoreInventoryManagementService.retrieveBooks(
        RETRIEVE_BY_NON_EXISTENT_GENRE)).isInstanceOf(StatusRuntimeException.class)
        .hasMessage(
            exceptionHandler.getBookNotFoundException(RETRIEVE_BY_NON_EXISTENT_GENRE).getMessage());
  }

  @Test
  @Order(11)
  void whenRetrievingBookByInvalidGenreThrowException() {
    assertThatThrownBy(() -> bookStoreInventoryManagementService.retrieveBooks(
        RETRIEVE_BY_INVALID_GENRE)).isInstanceOf(StatusRuntimeException.class)
        .hasMessage(
            exceptionHandler.getInvalidParameterException(RETRIEVE_BY_INVALID_GENRE).getMessage());
  }

  @Test
  @Order(12)
  void whenDeletingBookWithNonExistingIsbnThrowException() {
    assertThatThrownBy(() -> bookStoreInventoryManagementService.removeBook(
        RETRIEVE_BY_NON_EXISTENT_ISBN.getRequestValue())).isInstanceOf(StatusRuntimeException.class)
        .hasMessage(
            exceptionHandler.getBookNotFoundException(RETRIEVE_BY_NON_EXISTENT_ISBN).getMessage());
  }

  @Test
  @Order(13)
  void whenDeletingBookWithExistingIsbnReturnDeletedBook() {
    var removedBook = bookStoreInventoryManagementService.removeBook(UPDATED_BOOK.getIsbn());
    assertEquals(removedBook.getIsbn(), UPDATED_BOOK.getIsbn());
    assertEquals(removedBook.getTitle(), UPDATED_BOOK.getTitle());
    bookStoreInventoryManagementService.addBook(UPDATED_BOOK);
  }
}
