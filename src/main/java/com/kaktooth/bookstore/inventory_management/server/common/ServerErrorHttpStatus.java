package com.kaktooth.bookstore.inventory_management.server.common;

import com.google.rpc.Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Server gRPC error codes to http status mappings. See canonical error codes {@link Code}.
 */
@RequiredArgsConstructor
@Getter
public enum ServerErrorHttpStatus {
  OK(Code.OK, HttpStatus.OK),
  CANCELLED(Code.CANCELLED, HttpStatus.BAD_GATEWAY),
  UNKNOWN(Code.UNKNOWN, HttpStatus.INTERNAL_SERVER_ERROR),
  INVALID_ARGUMENT(Code.INVALID_ARGUMENT, HttpStatus.BAD_REQUEST),
  DEADLINE_EXCEEDED(Code.DEADLINE_EXCEEDED, HttpStatus.GATEWAY_TIMEOUT),
  NOT_FOUND(Code.NOT_FOUND, HttpStatus.NOT_FOUND),
  ALREADY_EXISTS(Code.ALREADY_EXISTS, HttpStatus.CONFLICT),
  PERMISSION_DENIED(Code.PERMISSION_DENIED, HttpStatus.FORBIDDEN),
  UNAUTHENTICATED(Code.UNAUTHENTICATED, HttpStatus.UNAUTHORIZED),
  RESOURCE_EXHAUSTED(Code.RESOURCE_EXHAUSTED, HttpStatus.TOO_MANY_REQUESTS),
  FAILED_PRECONDITION(Code.FAILED_PRECONDITION, HttpStatus.BAD_REQUEST),
  ABORTED(Code.ABORTED, HttpStatus.CONFLICT),
  OUT_OF_RANGE(Code.OUT_OF_RANGE, HttpStatus.BAD_REQUEST),
  UNIMPLEMENTED(Code.UNIMPLEMENTED, HttpStatus.NOT_IMPLEMENTED),
  INTERNAL(Code.INTERNAL, HttpStatus.INTERNAL_SERVER_ERROR),
  UNAVAILABLE(Code.UNAVAILABLE, HttpStatus.SERVICE_UNAVAILABLE),
  DATA_LOSS(Code.DATA_LOSS, HttpStatus.INTERNAL_SERVER_ERROR);

  private final Code serverErrorCode;
  private final HttpStatus httpStatus;
}
