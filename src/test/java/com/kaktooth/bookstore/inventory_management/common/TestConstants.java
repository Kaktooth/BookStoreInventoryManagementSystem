package com.kaktooth.bookstore.inventory_management.common;


import com.kaktooth.bookstore.inventory_management.client.handler.ErrorMessage;
import com.kaktooth.bookstore.inventory_management.protobuf.Book;
import com.kaktooth.bookstore.inventory_management.protobuf.BookRetrieveOption;
import com.kaktooth.bookstore.inventory_management.protobuf.Genre;
import com.kaktooth.bookstore.inventory_management.protobuf.RetrieveRequest;
import com.kaktooth.bookstore.inventory_management.server.model.dto.BookDto;
import com.kaktooth.bookstore.inventory_management.server.model.entity.BookGenre;
import java.util.Date;
import java.util.UUID;
import org.springframework.http.HttpStatus;

public class TestConstants {

  public static class Dto {
    public static final BookDto BOOK_DTO = BookDto.builder()
        .title("Tim Adventures")
        .author("Tim Parent")
        .isbn("9783151345100")
        .genre(Genre.ADVENTURE.name())
        .quantity(4)
        .build();

    public static final BookDto BOOK_DTO_COPY = BookDto.builder()
        .title("Tim Adventures")
        .author("Tim Parent")
        .isbn("9783151345100")
        .genre(Genre.ADVENTURE.name())
        .quantity(4)
        .build();

    public static final BookDto UPDATED_BOOK = BookDto.builder()
        .title("Tim New Adventures 2")
        .author("Tim Ainslie")
        .isbn("9783151345100")
        .genre(Genre.COMEDY.name())
        .quantity(3)
        .build();

    public static final BookDto BOOK_WITH_NON_EXISTENT_ISBN = BookDto.builder()
        .title("Tim New Adventures 2")
        .author("Tim Ainslie")
        .isbn("9712341234100")
        .genre(Genre.COMEDY.name())
        .quantity(3)
        .build();

    public static final BookDto INVALID_BOOK = BookDto.builder()
        .title("T")
        .author("A")
        .isbn("9783151345100")
        .genre(Genre.COMEDY.name())
        .quantity(3)
        .build();
  }

  public static class Protobuf {
    public static final Book BOOK_PROTOBUF = Book.newBuilder()
        .setTitle("Tim Adventures")
        .setAuthor("Tim Parent")
        .setIsbn("9783151345100")
        .setGenre(Genre.ADVENTURE)
        .setQuantity(4)
        .build();

    public static final Book BOOK_PROTOBUF_COPY = Book.newBuilder()
        .setTitle("Tim Adventures")
        .setAuthor("Tim Parent")
        .setIsbn("9783151345100")
        .setGenre(Genre.ADVENTURE)
        .setQuantity(4)
        .build();

    public static final Book UPDATED_BOOK = Book.newBuilder()
        .setTitle("Tim New Adventures 2")
        .setAuthor("Tim Ainslie")
        .setIsbn("9783151345100")
        .setGenre(Genre.COMEDY)
        .setQuantity(3)
        .build();

    public static final Book BOOK_WITH_NON_EXISTING_ISBN = Book.newBuilder()
        .setTitle("Tim New Adventures 2")
        .setAuthor("Tim Ainslie")
        .setIsbn("9712341234100")
        .setGenre(Genre.COMEDY)
        .setQuantity(3)
        .build();

    public static final Book INVALID_BOOK = Book.newBuilder()
        .setTitle("T")
        .setAuthor("A")
        .setIsbn("9783151345100")
        .setGenre(Genre.COMEDY)
        .setQuantity(3)
        .build();
  }

  public static final class Entity {

    public static final com.kaktooth.bookstore.inventory_management.server.model.entity.Book
        BOOK_ENTITY =
        com.kaktooth.bookstore.inventory_management.server.model.entity.Book.builder()
            .id(UUID.fromString("56ab2501-4f75-4e07-9c4e-83588df9e1a7"))
            .title("Test Book")
            .author("Tim Parent")
            .isbn("9783151345100")
            .genre(BookGenre.builder().id(3).name(Genre.ADVENTURE.name()).build())
            .quantity(4)
            .build();

    public static final com.kaktooth.bookstore.inventory_management.server.model.entity.Book
        BOOK_ENTITY_COPY =
        com.kaktooth.bookstore.inventory_management.server.model.entity.Book.builder()
            .id(UUID.fromString("56ab2501-4f75-4e07-9c4e-83588df9e1a7"))
            .title("Test Book")
            .author("Tim Parent")
            .isbn("9783151345100")
            .genre(BookGenre.builder().id(3).name(Genre.ADVENTURE.name()).build())
            .quantity(4)
            .build();
  }

  public static class Request {
    public static final RetrieveRequest RETRIEVE_BY_ISBN = RetrieveRequest.newBuilder()
        .setBookRetrieveOption(BookRetrieveOption.BY_ISBN)
        .setRequestValue("9783151345100")
        .build();

    public static final RetrieveRequest RETRIEVE_BY_ISBN_COPY = RetrieveRequest.newBuilder()
        .setBookRetrieveOption(BookRetrieveOption.BY_ISBN)
        .setRequestValue("9783151345100")
        .build();

    public static final RetrieveRequest RETRIEVE_BY_NON_EXISTENT_ISBN = RetrieveRequest.newBuilder()
        .setBookRetrieveOption(BookRetrieveOption.BY_ISBN)
        .setRequestValue("9787654345100")
        .build();

    public static final RetrieveRequest RETRIEVE_BY_AUTHOR = RetrieveRequest.newBuilder()
        .setBookRetrieveOption(BookRetrieveOption.BY_AUTHOR)
        .setRequestValue("Tim Ainslie")
        .build();

    public static final RetrieveRequest RETRIEVE_BY_NON_EXISTENT_AUTHOR =
        RetrieveRequest.newBuilder()
            .setBookRetrieveOption(BookRetrieveOption.BY_AUTHOR)
            .setRequestValue("Not existing author")
            .build();

    public static final RetrieveRequest RETRIEVE_BY_GENRE = RetrieveRequest.newBuilder()
        .setBookRetrieveOption(BookRetrieveOption.BY_GENRE)
        .setRequestValue(Genre.COMEDY.name())
        .build();

    public static final RetrieveRequest RETRIEVE_BY_NON_EXISTENT_GENRE =
        RetrieveRequest.newBuilder()
            .setBookRetrieveOption(BookRetrieveOption.BY_GENRE)
            .setRequestValue(Genre.PARANORMAL.name())
            .build();

    public static final RetrieveRequest RETRIEVE_BY_INVALID_GENRE = RetrieveRequest.newBuilder()
        .setBookRetrieveOption(BookRetrieveOption.BY_GENRE)
        .setRequestValue("Not existing genre")
        .build();
  }

  public static class ApiPath {
    public static final String ADD_BOOK = "/addBook";
    public static final String REMOVE_BOOK = "/removeBook/" + Dto.UPDATED_BOOK.getIsbn();
    public static final String UPDATE_BOOK = "/updateBook";
    public static final String BOOK_BY_ISBN = "/book/BY_ISBN/" + Dto.UPDATED_BOOK.getIsbn();
    public static final String BOOK_BY_TITLE = "/book/BY_TITLE/" + Dto.UPDATED_BOOK.getTitle();
    public static final String BOOK_BY_AUTHOR = "/book/BY_AUTHOR/" + Dto.UPDATED_BOOK.getAuthor();
    public static final String BOOK_BY_GENRE = "/book/BY_GENRE/" + Dto.UPDATED_BOOK.getGenre();
    public static final String BOOK_BY_INVALID_GENRE = "/book/BY_GENRE/not_existing_genre";
  }

  public static class ValidationMessage {

    public static final ErrorMessage ERROR_MESSAGE = ErrorMessage.builder()
        .url("/book/BY_GENRE/PHILOSOPHY")
        .description("Requested value for retrieving option is correct but book/books not exists.")
        .httpStatus(HttpStatus.NOT_FOUND.value())
        .date(new Date())
        .build();

    public static final ErrorMessage ERROR_MESSAGE_COPY = ErrorMessage.builder()
        .url("/book/BY_GENRE/PHILOSOPHY")
        .description("Requested value for retrieving option is correct but book/books not exists.")
        .httpStatus(HttpStatus.NOT_FOUND.value())
        .date(new Date())
        .build();

    public static final String NOT_FOUND_MESSAGE = "@contains(book/books by)@";
    public static final String ALREADY_EXISTS_MESSAGE =
        "@contains(ALREADY_EXISTS: Cant create book with title or isbn that already exists.)@";
    public static final String INVALID_ARGUMENT_MESSAGE =
        "@contains(INVALID_ARGUMENT: Cant retrieve book/books because of invalid parameter:)@";
  }
}
