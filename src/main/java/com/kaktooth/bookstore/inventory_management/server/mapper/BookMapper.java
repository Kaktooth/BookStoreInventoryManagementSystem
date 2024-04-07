package com.kaktooth.bookstore.inventory_management.server.mapper;

import com.kaktooth.bookstore.inventory_management.server.model.dto.BookDto;
import com.kaktooth.bookstore.inventory_management.server.model.entity.Book;
import jakarta.annotation.Nonnull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookMapper {
  BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

  @Mapping(source = "genre.name", target = "genre")
  @Nonnull
  BookDto mapToDto(@Nonnull Book book);

  @Mapping(target = "genre.id", expression = "java(com.kaktooth.bookstore.inventory_management.protobuf.Genre.valueOf(bookDto.getGenre()).ordinal())")
  @Mapping(source = "genre", target = "genre.name")
  @Nonnull
  Book mapToEntity(@Nonnull BookDto bookDto);

  @Mapping(target = "genre", expression = "java(book.getGenre().name())")
  @Nonnull
  BookDto mapToDto(@Nonnull com.kaktooth.bookstore.inventory_management.protobuf.Book book);

  @Mapping(target = "genre.id", expression = "java(genre.ordinal())")
  @Mapping(target = "genre.name", expression = "java(genre.name())")
  @Nonnull
  Book protoToEntity(@Nonnull com.kaktooth.bookstore.inventory_management.protobuf.Book book);

  @Mapping(target = "genre", expression = "java(com.kaktooth.bookstore.inventory_management.protobuf.Genre.valueOf(book.getGenre().getName()))")
  @Nonnull
  com.kaktooth.bookstore.inventory_management.protobuf.Book mapToProto(@Nonnull Book book);

  @Mapping(target = "genre", expression = "java(com.kaktooth.bookstore.inventory_management.protobuf.Genre.valueOf(bookDto.getGenre()))")
  @Nonnull
  com.kaktooth.bookstore.inventory_management.protobuf.Book mapToProto(@Nonnull BookDto bookDto);
}
