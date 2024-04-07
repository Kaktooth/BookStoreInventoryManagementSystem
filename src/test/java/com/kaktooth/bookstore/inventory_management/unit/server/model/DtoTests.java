package com.kaktooth.bookstore.inventory_management.unit.server.model;

import static com.kaktooth.bookstore.inventory_management.common.TestConstants.Dto.BOOK_DTO;
import static com.kaktooth.bookstore.inventory_management.common.TestConstants.Dto.BOOK_DTO_COPY;
import static com.kaktooth.bookstore.inventory_management.common.TestConstants.Dto.INVALID_BOOK;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class DtoTests {
  @Test
  void whenBookDtoIsEqualToBookDtoCopyThanReturnTrue() {
    assertThat(BOOK_DTO).isEqualTo(BOOK_DTO_COPY);
    assertThat(BOOK_DTO.hashCode()).hasSameHashCodeAs(BOOK_DTO_COPY.hashCode());
    assertThat(BOOK_DTO).isNotEqualTo(INVALID_BOOK);
    assertThat(BOOK_DTO.getTitle()).isEqualTo(BOOK_DTO_COPY.getTitle());
    assertThat(BOOK_DTO.getAuthor()).isEqualTo(BOOK_DTO_COPY.getAuthor());
    assertThat(BOOK_DTO.getQuantity()).isEqualTo(BOOK_DTO_COPY.getQuantity());
    assertThat(BOOK_DTO.getIsbn()).isEqualTo(BOOK_DTO_COPY.getIsbn());
    assertThat(BOOK_DTO.getGenre()).isEqualTo(BOOK_DTO_COPY.getGenre());
    assertThat(BOOK_DTO.toString()).hasToString(BOOK_DTO_COPY.toString());
  }
}
