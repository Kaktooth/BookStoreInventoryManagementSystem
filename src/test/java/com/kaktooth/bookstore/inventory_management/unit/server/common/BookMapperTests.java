package com.kaktooth.bookstore.inventory_management.unit.server.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.kaktooth.bookstore.inventory_management.protobuf.Book;
import com.kaktooth.bookstore.inventory_management.protobuf.Genre;
import com.kaktooth.bookstore.inventory_management.server.mapper.BookMapper;
import com.kaktooth.bookstore.inventory_management.server.model.dto.BookDto;
import com.kaktooth.bookstore.inventory_management.server.model.entity.BookGenre;
import org.junit.jupiter.api.Test;

class BookMapperTests {

  final Book bookProto = Book.newBuilder()
      .setTitle("Tim Adventures")
      .setAuthor("Tim Parent")
      .setIsbn("9783151345100")
      .setGenre(Genre.ADVENTURE)
      .setQuantity(4)
      .build();

  final BookDto bookDto = BookDto.builder()
      .title("Tim Adventures")
      .author("Tim Parent")
      .isbn("9783151345100")
      .genre(Genre.ADVENTURE.name())
      .quantity(4)
      .build();

  final com.kaktooth.bookstore.inventory_management.server.model.entity.Book bookEntity =
      com.kaktooth.bookstore.inventory_management.server.model.entity.Book.builder()
          .title("Tim Adventures")
          .author("Tim Parent")
          .isbn("9783151345100")
          .genre(BookGenre.builder()
              .id(Genre.ADVENTURE.ordinal())
              .name(Genre.ADVENTURE.name())
              .build())
          .quantity(4)
          .build();

  @Test
  void entityToDtoShouldReturnBookDto() {
    var dto = BookMapper.INSTANCE.mapToDto(bookEntity);
    assertEquals(dto.getIsbn(), bookDto.getIsbn());
    assertEquals(dto.getTitle(), bookDto.getTitle());
    assertEquals(dto.getAuthor(), bookDto.getAuthor());
    assertEquals(dto.getGenre(), bookDto.getGenre());
    assertEquals(dto.getQuantity(), bookDto.getQuantity());
  }

  @Test
  void dtoToEntityShouldReturnBookEntity() {
    var entity = BookMapper.INSTANCE.mapToEntity(bookDto);
    assertEquals(entity.getIsbn(), bookEntity.getIsbn());
    assertEquals(entity.getTitle(), bookEntity.getTitle());
    assertEquals(entity.getAuthor(), bookEntity.getAuthor());
    assertEquals(entity.getGenre(), bookEntity.getGenre());
    assertEquals(entity.getQuantity(), bookEntity.getQuantity());
  }


  @Test
  void protoToDtoShouldReturnBookDto() {
    var bookDto = BookMapper.INSTANCE.mapToDto(bookProto);
    assertEquals(bookDto.getIsbn(), bookProto.getIsbn());
    assertEquals(bookDto.getTitle(), bookProto.getTitle());
    assertEquals(bookDto.getAuthor(), bookProto.getAuthor());
    assertEquals(bookDto.getGenre(), bookProto.getGenre().name());
    assertEquals(bookDto.getQuantity(), bookProto.getQuantity());
  }

  @Test
  void protoToBookEntityShouldReturnBookEntity() {
    var entity = BookMapper.INSTANCE.protoToEntity(bookProto);
    assertEquals(entity.getIsbn(), bookEntity.getIsbn());
    assertEquals(entity.getTitle(), bookEntity.getTitle());
    assertEquals(entity.getAuthor(), bookEntity.getAuthor());
    assertEquals(entity.getGenre(), bookEntity.getGenre());
    assertEquals(entity.getQuantity(), bookEntity.getQuantity());
  }

  @Test
  void dtoToProtoShouldReturnBookProtobuf() {
    var protobuf = BookMapper.INSTANCE.mapToProto(bookDto);
    assertEquals(protobuf.getIsbn(), bookProto.getIsbn());
    assertEquals(protobuf.getTitle(), bookProto.getTitle());
    assertEquals(protobuf.getAuthor(), bookProto.getAuthor());
    assertEquals(protobuf.getGenre(), bookProto.getGenre());
    assertEquals(protobuf.getQuantity(), bookProto.getQuantity());
  }

  @Test
  void entityToProtoShouldReturnBookProtobuf() {
    var protobuf = BookMapper.INSTANCE.mapToProto(bookEntity);
    assertEquals(protobuf.getIsbn(), bookProto.getIsbn());
    assertEquals(protobuf.getTitle(), bookProto.getTitle());
    assertEquals(protobuf.getAuthor(), bookProto.getAuthor());
    assertEquals(protobuf.getGenre(), bookProto.getGenre());
    assertEquals(protobuf.getQuantity(), bookProto.getQuantity());
  }
}
