package com.kaktooth.bookstore.inventory_management.unit.client;

import static com.kaktooth.bookstore.inventory_management.common.TestConstants.Protobuf.BOOK_PROTOBUF;
import static com.kaktooth.bookstore.inventory_management.common.TestConstants.Protobuf.BOOK_WITH_NON_EXISTING_ISBN;
import static com.kaktooth.bookstore.inventory_management.common.TestConstants.Protobuf.INVALID_BOOK;
import static com.kaktooth.bookstore.inventory_management.common.TestConstants.Protobuf.UPDATED_BOOK;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.kaktooth.bookstore.inventory_management.configuration.GrpcTestConfiguration;
import com.kaktooth.bookstore.inventory_management.protobuf.BookRetrieveOption;
import com.kaktooth.bookstore.inventory_management.protobuf.BookStoreInventoryManagerGrpc;
import com.kaktooth.bookstore.inventory_management.protobuf.RetrieveRequest;
import com.kaktooth.bookstore.inventory_management.server.exception.ExceptionHandler;
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
class BookStoreInventoryManagementServiceStubTests {

  @Autowired
  BookStoreInventoryManagerGrpc.BookStoreInventoryManagerBlockingStub blockingStub;

  @Autowired
  ExceptionHandler exceptionHandler;

  @Test
  @Order(1)
  void whenBookAddedWithSameIsbnShouldThrowException() {
    var uploadedBook = blockingStub.addBook(BOOK_PROTOBUF);
    assertThat(uploadedBook).isNotNull();
    assertThat(uploadedBook.getTitle()).isEqualTo(BOOK_PROTOBUF.getTitle());
    assertThat(uploadedBook.getAuthor()).isEqualTo(BOOK_PROTOBUF.getAuthor());
    assertThat(uploadedBook.getIsbn()).isEqualTo(BOOK_PROTOBUF.getIsbn());
    assertThat(uploadedBook.getGenre()).isEqualTo(BOOK_PROTOBUF.getGenre());

    assertThatThrownBy(() -> blockingStub.addBook(BOOK_PROTOBUF)).isInstanceOf(
            StatusRuntimeException.class)
        .hasMessage(exceptionHandler.getBookAlreadyExistsException(BOOK_PROTOBUF).getMessage())
        .message();
  }

  @Test
  @Order(2)
  void whenUpdatingValidBookReturnUpdatedBook() {
    var newlyUpdatedBook = blockingStub.updateBook(UPDATED_BOOK);
    assertThat(newlyUpdatedBook).isNotNull();
    assertThat(newlyUpdatedBook.getTitle()).isEqualTo(UPDATED_BOOK.getTitle());
    assertThat(newlyUpdatedBook.getAuthor()).isEqualTo(UPDATED_BOOK.getAuthor());
    assertThat(newlyUpdatedBook.getGenre()).isEqualTo(UPDATED_BOOK.getGenre());

    assertThat(BOOK_PROTOBUF.getIsbn()).isEqualTo(UPDATED_BOOK.getIsbn());
  }

  @Test
  @Order(3)
  void whenUploadedBookIsNotFoundByIsbnForUpdateThrowException() {

    final var bookUpdateRetrieveRequest = RetrieveRequest.newBuilder()
        .setBookRetrieveOption(BookRetrieveOption.BY_ISBN)
        .setRequestValue(BOOK_WITH_NON_EXISTING_ISBN.getIsbn())
        .build();

    assertThatThrownBy(() -> blockingStub.updateBook(BOOK_WITH_NON_EXISTING_ISBN)).isInstanceOf(
            StatusRuntimeException.class)
        .hasMessage(
            exceptionHandler.getBookNotFoundException(bookUpdateRetrieveRequest).getMessage());
  }

  @Test
  @Order(4)
  void whenBookParamsForUpdateIsInvalidThrowException() {
    assertThatThrownBy(() -> blockingStub.updateBook(INVALID_BOOK)).isInstanceOf(
            StatusRuntimeException.class)
        .hasMessage(exceptionHandler.getInvalidBookParametersException(INVALID_BOOK).getMessage());
  }

  @Test
  @Order(5)
  void whenRetrievingBookByValidIsbnReturnBook() {
    var retrievedBookByIsbn = blockingStub.retrieveBooks(RETRIEVE_BY_ISBN);
    assertTrue(retrievedBookByIsbn.hasNext());
    assertEquals(retrievedBookByIsbn.next().getIsbn(), RETRIEVE_BY_ISBN.getRequestValue());
  }

  @Test
  @Order(6)
  void whenRetrievingBookByNotExistingIsbnThrowException() {
    assertThatThrownBy(
        () -> {
          var retrievedBooks = blockingStub.retrieveBooks(RETRIEVE_BY_NON_EXISTENT_ISBN);
          assertFalse(retrievedBooks.hasNext());
        }).isInstanceOf(StatusRuntimeException.class)
        .hasMessage(
            exceptionHandler.getBookNotFoundException(RETRIEVE_BY_NON_EXISTENT_ISBN).getMessage());
  }

  @Test
  @Order(7)
  void whenRetrievingBookByValidAuthorReturnBooks() {
    var retrievedBookByIsbn = blockingStub.retrieveBooks(RETRIEVE_BY_AUTHOR);
    assertTrue(retrievedBookByIsbn.hasNext());
    assertEquals(retrievedBookByIsbn.next().getAuthor(), RETRIEVE_BY_AUTHOR.getRequestValue());
  }

  @Test
  @Order(8)
  void whenRetrievingBookByNotExistingAuthorThrowException() {
    assertThatThrownBy(
        () -> {
          var retrievedBooks = blockingStub.retrieveBooks(RETRIEVE_BY_NON_EXISTENT_AUTHOR);
          assertFalse(retrievedBooks.hasNext());
        }).isInstanceOf(StatusRuntimeException.class)
        .hasMessage(exceptionHandler.getBookNotFoundException(RETRIEVE_BY_NON_EXISTENT_AUTHOR)
            .getMessage());
  }

  @Test
  @Order(9)
  void whenRetrievingBookByValidGenreReturnBooks() {
    var retrievedBookByIsbn = blockingStub.retrieveBooks(RETRIEVE_BY_GENRE);
    assertTrue(retrievedBookByIsbn.hasNext());
    assertEquals(retrievedBookByIsbn.next().getGenre().name(), RETRIEVE_BY_GENRE.getRequestValue());
  }

  @Test
  @Order(10)
  void whenRetrievingNotExistingBooksByGenreThrowException() {
    assertThatThrownBy(
        () -> {
          var retrievedBooks = blockingStub.retrieveBooks(RETRIEVE_BY_NON_EXISTENT_GENRE);
          assertFalse(retrievedBooks.hasNext());
        }).isInstanceOf(StatusRuntimeException.class)
        .hasMessage(
            exceptionHandler.getBookNotFoundException(RETRIEVE_BY_NON_EXISTENT_GENRE).getMessage());
  }

  @Test
  @Order(11)
  void whenRetrievingBookByInvalidGenreThrowException() {
    assertThatThrownBy(
        () -> {
          var retrievedBooks = blockingStub.retrieveBooks(RETRIEVE_BY_INVALID_GENRE);
          assertFalse(retrievedBooks.hasNext());
        }).isInstanceOf(StatusRuntimeException.class)
        .hasMessage(
            exceptionHandler.getInvalidParameterException(RETRIEVE_BY_INVALID_GENRE).getMessage());
  }

  @Test
  @Order(12)
  void whenDeletingBookWithExistingIsbnDeleteBook() {
    var deletedBook = blockingStub.deleteBook(BOOK_PROTOBUF);
    assertThat(deletedBook).isNotNull();
    assertThat(deletedBook.getTitle()).isEqualTo(BOOK_PROTOBUF.getTitle());
    assertThat(deletedBook.getAuthor()).isEqualTo(BOOK_PROTOBUF.getAuthor());
    assertThat(deletedBook.getGenre()).isEqualTo(BOOK_PROTOBUF.getGenre());
    assertThat(deletedBook.getIsbn()).isEqualTo(BOOK_PROTOBUF.getIsbn());
    assertThatThrownBy(
        () -> {
          var retrievedBooks = blockingStub.retrieveBooks(RETRIEVE_BY_ISBN);
          assertFalse(retrievedBooks.hasNext());
        }).isInstanceOf(StatusRuntimeException.class)
        .hasMessage(exceptionHandler.getBookNotFoundException(RETRIEVE_BY_ISBN).getMessage());
  }
}
