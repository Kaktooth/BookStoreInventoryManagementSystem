package com.kaktooth.bookstore.inventory_management.unit.server.common;

import static com.kaktooth.bookstore.inventory_management.common.TestConstants.ValidationMessage.ERROR_MESSAGE;
import static com.kaktooth.bookstore.inventory_management.common.TestConstants.ValidationMessage.ERROR_MESSAGE_COPY;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ErrorMessageTest {

  @Test
  void whenErrorMessageIsEqualToMessageCopyReturnTrue() {
    assertThat(ERROR_MESSAGE).isEqualTo(ERROR_MESSAGE_COPY);
    assertThat(ERROR_MESSAGE).hasSameHashCodeAs(ERROR_MESSAGE_COPY);
    assertThat(ERROR_MESSAGE.getDate()).isEqualTo(ERROR_MESSAGE_COPY.getDate());
    assertThat(ERROR_MESSAGE.getDescription()).isEqualTo(ERROR_MESSAGE_COPY.getDescription());
    assertThat(ERROR_MESSAGE.getUrl()).isEqualTo(ERROR_MESSAGE_COPY.getUrl());
    assertThat(ERROR_MESSAGE.getHttpStatus()).isEqualTo(ERROR_MESSAGE_COPY.getHttpStatus());
  }
}
