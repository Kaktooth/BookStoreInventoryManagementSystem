package com.kaktooth.bookstore.inventory_management.unit.server.common;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.rpc.Code;
import com.kaktooth.bookstore.inventory_management.server.common.ServerErrorHttpStatus;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class ServerErrorHttpStatusTest {
  private static int[] getErrorCodesRange() {
    return IntStream.range(0, 15).toArray();
  }

  @ParameterizedTest
  @MethodSource("com.kaktooth.bookstore.inventory_management.unit.server.common.ServerErrorHttpStatusTest#getErrorCodesRange")
  void whenErrorCodeEqualToHttpStatusCodeReturnTrue(int errorCode) {
    var code = Code.forNumber(errorCode);
    Assertions.assertThat(ServerErrorHttpStatus.valueOf(code.name()).getServerErrorCode())
        .isEqualTo(code);
    assertThat(ServerErrorHttpStatus.valueOf(code.name()).name()).isEqualTo(code.name());
    assertThat(ServerErrorHttpStatus.valueOf(code.name()).ordinal()).isEqualTo(code.ordinal());
  }
}
