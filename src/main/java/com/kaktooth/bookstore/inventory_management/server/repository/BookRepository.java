package com.kaktooth.bookstore.inventory_management.server.repository;

import com.kaktooth.bookstore.inventory_management.server.model.entity.Book;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, UUID> {

  Optional<List<Book>> findAllByTitle(String title);

  Optional<List<Book>> findAllByAuthor(String author);

  Optional<List<Book>> findAllByGenreId(Integer genreId);

  Optional<Book> findBookByIsbn(String isbn);

  void deleteBookByIsbn(String isbn);

  Boolean existsBookByIsbn(String isbn);
}
