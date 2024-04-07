package com.kaktooth.bookstore.inventory_management.unit.server.common;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.kaktooth.bookstore.inventory_management.server.common.ApplicationConstants;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class AppConstantsTest {

  @Test
  void appConstantShould() {
    var constantFields = Arrays.asList(ApplicationConstants.class.getDeclaredFields());
    constantFields.forEach(field -> {
      assertTrue(Modifier.isPublic(field.getModifiers()));
      assertTrue(Modifier.isStatic(field.getModifiers()));
      assertTrue(Modifier.isFinal(field.getModifiers()));
    });

    assertThrows(IllegalStateException.class, () ->
        ReflectionTestUtils.setField(ApplicationConstants.class,
            "BOOK_STORE_INVENTORY_SYSTEM_SERVER_DOMAIN", "hello"));
  }
}
