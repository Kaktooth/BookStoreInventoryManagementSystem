package com.kaktooth.bookstore.inventory_management.unit.server;

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
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

import com.kaktooth.bookstore.inventory_management.configuration.GrpcTestConfiguration;
import com.kaktooth.bookstore.inventory_management.protobuf.Book;
import com.kaktooth.bookstore.inventory_management.server.service.BookStoreInventoryManagementService;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = GrpcTestConfiguration.class)
class BookStoreInventoryManagementServiceTests {

  @Spy
  StreamObserver<Book> responseObserver;

  @Autowired
  BookStoreInventoryManagementService bookStoreInventoryManagementService;

  @Test
  @Order(1)
  void whenBookAddedWithSameIsbnShouldThrowException() {
    bookStoreInventoryManagementService.addBook(BOOK_PROTOBUF, responseObserver);

    bookStoreInventoryManagementService.addBook(BOOK_PROTOBUF, responseObserver);
    verify(responseObserver).onError(any(StatusRuntimeException.class));
  }

  @Test
  @Order(2)
  void whenBookForUpdateIsValidSendSuccessfulStreamCompletionNotification() {
    bookStoreInventoryManagementService.updateBook(UPDATED_BOOK, responseObserver);
    verify(responseObserver).onCompleted();
  }

  @Test
  @Order(3)
  void whenBookForUpdateIsNotFoundByIsbnThrowException() {
    bookStoreInventoryManagementService.updateBook(BOOK_WITH_NON_EXISTING_ISBN, responseObserver);
    verify(responseObserver).onError(any(StatusRuntimeException.class));
  }

  @Test
  @Order(4)
  void whenBookParamsForUpdateIsInvalidThrowException() {
    bookStoreInventoryManagementService.updateBook(INVALID_BOOK, responseObserver);
    verify(responseObserver).onError(any(StatusRuntimeException.class));
  }

  @Test
  @Order(5)
  void whenDeletingBookWithNonExistingIsbnThrowException() {
    bookStoreInventoryManagementService.deleteBook(BOOK_WITH_NON_EXISTING_ISBN, responseObserver);
    verify(responseObserver).onError(any(StatusRuntimeException.class));
  }

  @Test
  @Order(6)
  void whenDeletingBookWithExistingIsbnSendSuccessfulStreamCompletionNotification() {
    bookStoreInventoryManagementService.deleteBook(UPDATED_BOOK, responseObserver);
    verify(responseObserver).onCompleted();
    bookStoreInventoryManagementService.addBook(UPDATED_BOOK, responseObserver);
  }

  @Test
  @Order(7)
  void whenRetrievingBookByValidIsbnSendSuccessfulStreamCompletionNotification() {
    bookStoreInventoryManagementService.retrieveBooks(RETRIEVE_BY_ISBN, responseObserver);
    verify(responseObserver).onCompleted();
  }

  @Test
  @Order(8)
  void whenRetrievingBookByNonExistingIsbnThrowException() {
    bookStoreInventoryManagementService.retrieveBooks(RETRIEVE_BY_NON_EXISTENT_ISBN,
        responseObserver);
    verify(responseObserver).onError(any(StatusRuntimeException.class));
  }

  @Test
  @Order(9)
  void whenRetrievingBookByValidAuthorSendSuccessfulStreamCompletionNotification() {
    bookStoreInventoryManagementService.retrieveBooks(RETRIEVE_BY_AUTHOR, responseObserver);
    verify(responseObserver).onCompleted();
  }

  @Test
  @Order(10)
  void whenRetrievingBookByNonExistingAuthorThrowException() {
    bookStoreInventoryManagementService.retrieveBooks(RETRIEVE_BY_NON_EXISTENT_AUTHOR,
        responseObserver);
    verify(responseObserver).onError(any(StatusRuntimeException.class));
  }

  @Test
  @Order(11)
  void whenRetrievingBookByValidGenreSendSuccessfulStreamCompletionNotification() {
    bookStoreInventoryManagementService.retrieveBooks(RETRIEVE_BY_GENRE, responseObserver);
    verify(responseObserver).onCompleted();
  }

  @Test
  @Order(12)
  void whenRetrievingBookByNonExistingGenreThrowException() {
    bookStoreInventoryManagementService.retrieveBooks(RETRIEVE_BY_NON_EXISTENT_GENRE,
        responseObserver);
    verify(responseObserver).onError(any(StatusRuntimeException.class));
  }

  @Test
  @Order(13)
  void whenRetrievingBookByInvalidGenreThrowException() {
    bookStoreInventoryManagementService.retrieveBooks(RETRIEVE_BY_INVALID_GENRE, responseObserver);
    verify(responseObserver).onError(any(StatusRuntimeException.class));
  }
}
