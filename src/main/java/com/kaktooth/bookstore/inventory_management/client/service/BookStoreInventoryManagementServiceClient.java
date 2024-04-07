package com.kaktooth.bookstore.inventory_management.client.service;

import com.kaktooth.bookstore.inventory_management.protobuf.BookRetrieveOption;
import com.kaktooth.bookstore.inventory_management.protobuf.BookStoreInventoryManagerGrpc;
import com.kaktooth.bookstore.inventory_management.protobuf.RetrieveRequest;
import com.kaktooth.bookstore.inventory_management.server.mapper.BookMapper;
import com.kaktooth.bookstore.inventory_management.server.model.dto.BookDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookStoreInventoryManagementServiceClient {
  private final BookStoreInventoryManagerGrpc.BookStoreInventoryManagerBlockingStub blockingStub;
  private final BookMapper bookMapper;

  public BookDto addBook(BookDto bookDto) {
    var bookProto = bookMapper.mapToProto(bookDto);
    var newBook = blockingStub.addBook(bookProto);
    log.info("New book with title '{}' is created.", newBook.getTitle());
    return bookMapper.mapToDto(newBook);
  }

  public BookDto updateBook(BookDto bookDto) {
    var bookProto = bookMapper.mapToProto(bookDto);
    var updatedBook = blockingStub.updateBook(bookProto);
    log.info("Book '{}' is updated.", updatedBook.getTitle());
    return bookMapper.mapToDto(updatedBook);
  }

  public BookDto removeBook(String isbn) {

    var retrieveRequest =
        com.kaktooth.bookstore.inventory_management.protobuf.RetrieveRequest.newBuilder()
            .setBookRetrieveOption(BookRetrieveOption.BY_ISBN)
            .setRequestValue(isbn)
            .build();

    var books = retrieveBooks(retrieveRequest);

    if (books.iterator().hasNext()) {
      var retrievedBook = retrieveBooks(retrieveRequest).iterator().next();
      var removedBook = blockingStub.deleteBook(bookMapper.mapToProto(retrievedBook));
      log.info("Book '{}' is removed.", removedBook.getTitle());
      return bookMapper.mapToDto(removedBook);
    } else {
      return BookDto.builder().build();
    }
  }

  public List<BookDto> retrieveBooks(RetrieveRequest retrieveRequest) {

    var books = new ArrayList<BookDto>();
    var retrievedBooks = blockingStub.retrieveBooks(retrieveRequest);

    while (retrievedBooks.hasNext()) {
      var retrievedBook = retrievedBooks.next();
      books.add(bookMapper.mapToDto(retrievedBook));
      log.info("Book '{}' is retrieved.", retrievedBook.getTitle());
    }
    return books;
  }
}
