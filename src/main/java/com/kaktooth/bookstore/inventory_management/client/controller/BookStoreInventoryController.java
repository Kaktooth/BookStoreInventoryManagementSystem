package com.kaktooth.bookstore.inventory_management.client.controller;

import com.kaktooth.bookstore.inventory_management.client.service.BookStoreInventoryManagementServiceClient;
import com.kaktooth.bookstore.inventory_management.protobuf.BookRetrieveOption;
import com.kaktooth.bookstore.inventory_management.protobuf.RetrieveRequest;
import com.kaktooth.bookstore.inventory_management.server.model.dto.BookDto;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class BookStoreInventoryController {
  private final BookStoreInventoryManagementServiceClient bookStoreInventoryManagementServiceClient;

  @PostMapping("/addBook")
  public ResponseEntity<BookDto> addBook(@RequestBody @Valid BookDto book) {
    var uploadedBook = bookStoreInventoryManagementServiceClient.addBook(book);
    return ResponseEntity.ok(uploadedBook);
  }

  @PutMapping("/updateBook")
  public ResponseEntity<BookDto> updateBook(@RequestBody @Valid BookDto book) {
    var updatedBook = bookStoreInventoryManagementServiceClient.updateBook(book);
    return ResponseEntity.ok(updatedBook);
  }

  @DeleteMapping("/removeBook/{isbn}")
  public ResponseEntity<BookDto> removeBook(@PathVariable String isbn) {
    var removedBook = bookStoreInventoryManagementServiceClient.removeBook(isbn);
    return ResponseEntity.ok(removedBook);
  }

  @GetMapping("/book/{retrieveOption}/{requestedValue}")
  public ResponseEntity<List<BookDto>> retrieveBooks(
      @PathVariable BookRetrieveOption retrieveOption,
      @PathVariable String requestedValue) {
    var retrieveRequest = RetrieveRequest.newBuilder()
        .setBookRetrieveOption(retrieveOption)
        .setRequestValue(requestedValue)
        .build();

    var retrievedBooks = bookStoreInventoryManagementServiceClient.retrieveBooks(retrieveRequest);

    return ResponseEntity.ok(retrievedBooks);
  }
}