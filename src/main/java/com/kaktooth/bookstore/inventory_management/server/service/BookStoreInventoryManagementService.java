package com.kaktooth.bookstore.inventory_management.server.service;

import com.kaktooth.bookstore.inventory_management.protobuf.Book;
import com.kaktooth.bookstore.inventory_management.protobuf.BookRetrieveOption;
import com.kaktooth.bookstore.inventory_management.protobuf.BookStoreInventoryManagerGrpc;
import com.kaktooth.bookstore.inventory_management.protobuf.Genre;
import com.kaktooth.bookstore.inventory_management.protobuf.RetrieveRequest;
import com.kaktooth.bookstore.inventory_management.server.exception.ExceptionHandler;
import com.kaktooth.bookstore.inventory_management.server.mapper.BookMapper;
import com.kaktooth.bookstore.inventory_management.server.model.entity.BookGenre;
import com.kaktooth.bookstore.inventory_management.server.repository.BookRepository;
import io.grpc.stub.StreamObserver;
import jakarta.transaction.Transactional;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BookStoreInventoryManagementService
    extends BookStoreInventoryManagerGrpc.BookStoreInventoryManagerImplBase {

  private final BookRepository bookRepository;
  private final BookMapper bookMapper;
  private final ExceptionHandler exceptionHandler;
  private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Override
  public void addBook(Book request, StreamObserver<Book> responseObserver) {
    if (validateRequest(request, responseObserver)) {
      var bookExists = bookRepository.existsBookByIsbn(request.getIsbn());
      if (Boolean.TRUE.equals(bookExists)) {
        responseObserver.onError(exceptionHandler.getBookAlreadyExistsException(request));
        return;
      }

      var uploadedBook = bookRepository.save(bookMapper.protoToEntity(request));
      var uploadedBookResponse = bookMapper.mapToProto(uploadedBook);
      responseObserver.onNext(uploadedBookResponse);
      responseObserver.onCompleted();
    }
  }

  @Override
  public void updateBook(Book request, StreamObserver<Book> responseObserver) {
    if (validateRequest(request, responseObserver)) {

      var requestByIsbn = RetrieveRequest.newBuilder()
          .setBookRetrieveOption(BookRetrieveOption.BY_ISBN)
          .setRequestValue(request.getIsbn())
          .build();

      var bookByIsbn = bookRepository.findBookByIsbn(requestByIsbn.getRequestValue());
      com.kaktooth.bookstore.inventory_management.server.model.entity.Book book;
      if (bookByIsbn.isPresent()) {
        book = bookByIsbn.get();
      } else {
        responseObserver.onError(exceptionHandler.getBookNotFoundException(requestByIsbn));
        return;
      }

      var newGenre = BookGenre.builder()
          .id(request.getGenreValue())
          .name(request.getGenre().name())
          .build();
      book.setTitle(request.getTitle());
      book.setAuthor(request.getAuthor());
      book.setGenre(newGenre);
      book.setQuantity(request.getQuantity());

      var updatedBook = bookRepository.save(book);
      var updatedBookResponse = bookMapper.mapToProto(updatedBook);
      responseObserver.onNext(updatedBookResponse);
      responseObserver.onCompleted();
    }
  }

  @Override
  public void deleteBook(Book request, StreamObserver<Book> responseObserver) {
    if (validateRequest(request, responseObserver)) {
      var bookExists = bookRepository.existsBookByIsbn(request.getIsbn());

      if (Boolean.FALSE.equals(bookExists)) {
        responseObserver.onError(exceptionHandler.getInvalidParameterException(request));
        return;
      }

      bookRepository.deleteBookByIsbn(request.getIsbn());

      responseObserver.onNext(request);
      responseObserver.onCompleted();
    }
  }

  private boolean validateRequest(Book request, StreamObserver<Book> responseObserver) {
    var validatorViolations = validator.validate(bookMapper.protoToEntity(request));
    if (!validatorViolations.isEmpty()) {
      responseObserver.onError(exceptionHandler.getInvalidBookParametersException(request));
      return false;
    }
    return true;
  }

  @Override
  public void retrieveBooks(RetrieveRequest request, StreamObserver<Book> responseObserver) {
    List<com.kaktooth.bookstore.inventory_management.server.model.entity.Book> books =
        new ArrayList<>();

    switch (request.getBookRetrieveOption()) {
      case BY_ISBN -> {
        var isbn = request.getRequestValue();
        var book = bookRepository.findBookByIsbn(isbn);

        if (book.isPresent()) {
          responseObserver.onNext(bookMapper.mapToProto(book.get()));
        } else {
          responseObserver.onError(exceptionHandler.getBookNotFoundException(request));
          return;
        }
        responseObserver.onCompleted();
        return;
      }
      case BY_TITLE -> {
        var title = request.getRequestValue();
        var booksByTitle = bookRepository.findAllByTitle(title);
        if (booksByTitle.isPresent()) {
          books = booksByTitle.get();
        }
      }
      case BY_AUTHOR -> {
        var author = request.getRequestValue();
        var booksByAuthor = bookRepository.findAllByAuthor(author);
        if (booksByAuthor.isPresent()) {
          books = booksByAuthor.get();
        }
      }
      case BY_GENRE -> {
        var genre = request.getRequestValue();
        try {
          var optionNumber = Genre.valueOf(genre).getNumber();
          var booksByGenre = bookRepository.findAllByGenreId(optionNumber);
          if (booksByGenre.isPresent()) {
            books = booksByGenre.get();
          }
        } catch (RuntimeException ex) {
          responseObserver.onError(exceptionHandler.getInvalidParameterException(request));
          return;
        }
      }
    }

    if (books.isEmpty()) {
      responseObserver.onError(exceptionHandler.getBookNotFoundException(request));
      return;
    }

    for (var book : books) {
      var retrievedBookResponse = bookMapper.mapToProto(book);
      responseObserver.onNext(retrievedBookResponse);
    }

    responseObserver.onCompleted();
  }
}
