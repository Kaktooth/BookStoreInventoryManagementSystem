package com.kaktooth.bookstore.inventory_management.unit.server.model;


import static com.kaktooth.bookstore.inventory_management.common.TestConstants.Entity.BOOK_ENTITY;
import static com.kaktooth.bookstore.inventory_management.common.TestConstants.Entity.BOOK_ENTITY_COPY;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class EntityTests {

  @Test
  void whenBookEntityIsEqualToBookEntityCopyThanReturnTrue() {
    assertThat(BOOK_ENTITY).isEqualTo(BOOK_ENTITY_COPY);
    assertThat(BOOK_ENTITY.hashCode()).hasSameHashCodeAs(BOOK_ENTITY_COPY.hashCode());
    assertThat(BOOK_ENTITY.getId()).isEqualTo(BOOK_ENTITY_COPY.getId());
    assertThat(BOOK_ENTITY.getTitle()).isEqualTo(BOOK_ENTITY_COPY.getTitle());
    assertThat(BOOK_ENTITY.getAuthor()).isEqualTo(BOOK_ENTITY_COPY.getAuthor());
    assertThat(BOOK_ENTITY.getQuantity()).isEqualTo(BOOK_ENTITY_COPY.getQuantity());
    assertThat(BOOK_ENTITY.getIsbn()).isEqualTo(BOOK_ENTITY_COPY.getIsbn());
    assertThat(BOOK_ENTITY.getGenre()).isEqualTo(BOOK_ENTITY_COPY.getGenre());
    assertThat(BOOK_ENTITY.toString()).hasToString(BOOK_ENTITY_COPY.toString());
  }
}
