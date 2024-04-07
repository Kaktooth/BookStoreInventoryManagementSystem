package com.kaktooth.bookstore.inventory_management.unit.server;

import static com.kaktooth.bookstore.inventory_management.common.TestConstants.Protobuf.BOOK_PROTOBUF;
import static com.kaktooth.bookstore.inventory_management.common.TestConstants.Protobuf.BOOK_PROTOBUF_COPY;
import static com.kaktooth.bookstore.inventory_management.common.TestConstants.Protobuf.INVALID_BOOK;
import static com.kaktooth.bookstore.inventory_management.common.TestConstants.Request.RETRIEVE_BY_ISBN;
import static com.kaktooth.bookstore.inventory_management.common.TestConstants.Request.RETRIEVE_BY_ISBN_COPY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.kaktooth.bookstore.inventory_management.protobuf.Genre;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class ProtobufTests {

  private static int[] getGenreRange() {
    return IntStream.range(0, 24).toArray();
  }

  @ParameterizedTest
  @MethodSource("com.kaktooth.bookstore.inventory_management.unit.server.ProtobufTests#getGenreRange")
  void whenEntityGenreEqualToProtobufGenreReturnTrue(int genreNumber) {
    if (genreNumber > 22) {
      assertThrows(NullPointerException.class, () -> Genre.forNumber(genreNumber).name());
      return;
    }
    var genreProto = Genre.forNumber(genreNumber);
    var genre = com.kaktooth.bookstore.inventory_management.server.model.entity.Genre.valueOf(
        genreProto.name());

    assertThat(genreProto.name()).isEqualTo(genre.name());
    assertThat(genreProto.getNumber()).isEqualTo(genre.ordinal());
    assertThat(genreProto.ordinal()).isEqualTo(genre.ordinal());
  }

  @Test
  void whenEntityGenreNotEqualToProtobufGenreReturnFalse() {
    assertThat(Genre.COMEDY.name()).isNotEqualTo(
        com.kaktooth.bookstore.inventory_management.server.model.entity.Genre.ADVENTURE.name());
    assertThat(Genre.COMEDY.ordinal()).isNotEqualTo(
        com.kaktooth.bookstore.inventory_management.server.model.entity.Genre.ADVENTURE.ordinal());
  }

  @Test
  void whenBookProtoIsEqualToBookProtoCopyThanReturnTrue() {
    assertThat(BOOK_PROTOBUF).isEqualTo(BOOK_PROTOBUF_COPY);
    assertThat(BOOK_PROTOBUF.hashCode()).hasSameHashCodeAs(BOOK_PROTOBUF_COPY.hashCode());
    assertThat(BOOK_PROTOBUF).isNotEqualTo(INVALID_BOOK);
    assertThat(BOOK_PROTOBUF.getTitle()).isEqualTo(BOOK_PROTOBUF_COPY.getTitle());
    assertThat(BOOK_PROTOBUF.getAuthor()).isEqualTo(BOOK_PROTOBUF_COPY.getAuthor());
    assertThat(BOOK_PROTOBUF.getQuantity()).isEqualTo(BOOK_PROTOBUF_COPY.getQuantity());
    assertThat(BOOK_PROTOBUF.getIsbn()).isEqualTo(BOOK_PROTOBUF_COPY.getIsbn());
    assertThat(BOOK_PROTOBUF.getGenre()).isEqualTo(BOOK_PROTOBUF_COPY.getGenre());
    assertThat(BOOK_PROTOBUF.getGenreValue()).isEqualTo(BOOK_PROTOBUF_COPY.getGenreValue());
    assertThat(BOOK_PROTOBUF.getAuthorBytes()).isEqualTo(BOOK_PROTOBUF_COPY.getAuthorBytes());
    assertThat(BOOK_PROTOBUF.getIsbnBytes()).isEqualTo(BOOK_PROTOBUF_COPY.getIsbnBytes());
    assertThat(BOOK_PROTOBUF.getTitleBytes()).isEqualTo(BOOK_PROTOBUF_COPY.getTitleBytes());
    assertThat(BOOK_PROTOBUF.getSerializedSize()).isEqualTo(BOOK_PROTOBUF_COPY.getSerializedSize());
  }

  @Test
  void whenRetrieveRequestIsEqualToCopyThanReturnTrue() {
    assertThat(RETRIEVE_BY_ISBN).isEqualTo(RETRIEVE_BY_ISBN_COPY);
    assertThat(RETRIEVE_BY_ISBN).hasSameHashCodeAs(RETRIEVE_BY_ISBN_COPY);
    assertThat(RETRIEVE_BY_ISBN.getRequestValue()).isEqualTo(
        RETRIEVE_BY_ISBN_COPY.getRequestValue());
    assertThat(RETRIEVE_BY_ISBN.getBookRetrieveOption()).isEqualTo(
        RETRIEVE_BY_ISBN_COPY.getBookRetrieveOption());
    assertThat(RETRIEVE_BY_ISBN.getBookRetrieveOption().getNumber()).isEqualTo(
        RETRIEVE_BY_ISBN_COPY.getBookRetrieveOption().getNumber());
    assertThat(RETRIEVE_BY_ISBN.getBookRetrieveOption().getValueDescriptor()).isEqualTo(
        RETRIEVE_BY_ISBN_COPY.getBookRetrieveOption().getValueDescriptor());
    assertThat(RETRIEVE_BY_ISBN.getBookRetrieveOption().getNumber()).isEqualTo(
        RETRIEVE_BY_ISBN_COPY.getBookRetrieveOption().getNumber());
    assertThat(RETRIEVE_BY_ISBN.getBookRetrieveOptionValue()).isEqualTo(
        RETRIEVE_BY_ISBN_COPY.getBookRetrieveOptionValue());
    assertThat(RETRIEVE_BY_ISBN.getRequestValueBytes()).isEqualTo(
        RETRIEVE_BY_ISBN_COPY.getRequestValueBytes());
    assertThat(RETRIEVE_BY_ISBN.getSerializedSize()).isEqualTo(
        RETRIEVE_BY_ISBN_COPY.getSerializedSize());
  }
}
