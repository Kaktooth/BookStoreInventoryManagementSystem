package com.kaktooth.bookstore.inventory_management.unit.server;

import static com.kaktooth.bookstore.inventory_management.common.TestConstants.Protobuf.BOOK_PROTOBUF;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.kaktooth.bookstore.inventory_management.configuration.PostgresContainer;
import com.kaktooth.bookstore.inventory_management.server.mapper.BookMapper;
import com.kaktooth.bookstore.inventory_management.server.model.entity.Book;
import com.kaktooth.bookstore.inventory_management.server.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.junit.ClassRule;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.transaction.AfterTransaction;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookRepositoryTests {

  @ClassRule
  public static PostgresContainer postgreSQLContainer = PostgresContainer.getInstance();

  @Autowired
  BookRepository bookRepository;

  Book book = BookMapper.INSTANCE.protoToEntity(BOOK_PROTOBUF);

  @AfterTransaction
  public void deleteEntities() {
    bookRepository.deleteAll();
  }

  @Test
  @Order(1)
  void whenSaveBookWithRepositoryReturnBook() {

    bookRepository.deleteAll();

    var savedBook = bookRepository.save(book);
    assertThat(savedBook).isNotNull();
    assertThat(savedBook).isEqualTo(book);
    assertThat(savedBook.getTitle()).isEqualTo(book.getTitle());
    assertThat(savedBook.getAuthor()).isEqualTo(book.getAuthor());
    assertThat(savedBook.getGenre()).isEqualTo(book.getGenre());
    assertThat(savedBook.getIsbn()).isEqualTo(book.getIsbn());
  }

  @Test
  @Order(2)
  void whenFindBooksByExistingAuthorReturnBook() {
    var optionalBooks = bookRepository.findAllByAuthor(book.getAuthor());
    assertTrue(optionalBooks.isPresent());
    var books = optionalBooks.get();
    assertFalse(books.isEmpty());
    assertThat(books.iterator().next().getAuthor()).isEqualTo(book.getAuthor());
  }

  @Test
  @Order(3)
  void whenFindBooksByExistingTitleReturnBook() {
    var optionalBooks = bookRepository.findAllByTitle(book.getTitle());
    assertTrue(optionalBooks.isPresent());
    var books = optionalBooks.get();
    assertFalse(books.isEmpty());
    assertThat(books.iterator().next().getTitle()).isEqualTo(book.getTitle());
  }

  @Test
  @Order(4)
  void whenFindBooksByExistingGenreReturnBook() {
    var optionalBooks = bookRepository.findAllByGenreId(book.getGenre().getId());
    assertTrue(optionalBooks.isPresent());
    var books = optionalBooks.get();
    assertFalse(books.isEmpty());
    assertThat(books.iterator().next().getAuthor()).isEqualTo(book.getAuthor());
  }

  @Test
  @Order(5)
  void whenFindBooksByExistingIsbnReturnBook() {
    var optionalBooks = bookRepository.findBookByIsbn(book.getIsbn());
    assertTrue(optionalBooks.isPresent());
    var retrievedBook = optionalBooks.get();
    assertThat(retrievedBook.getIsbn()).isEqualTo(book.getIsbn());
  }

  @Test
  @Order(6)
  void whenBookIsExistsReturnTrue() {
    assertTrue(bookRepository.existsBookByIsbn(book.getIsbn()));
  }

  @Test
  @Order(7)
  @Transactional
  void whenDeletingExistingBookThanBookNotExists() {
    bookRepository.deleteBookByIsbn(book.getIsbn());
    assertFalse(bookRepository.existsBookByIsbn(book.getIsbn()));
  }
}
