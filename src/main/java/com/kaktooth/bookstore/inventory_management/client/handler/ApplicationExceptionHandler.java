package com.kaktooth.bookstore.inventory_management.client.handler;

import com.kaktooth.bookstore.inventory_management.server.common.ServerErrorHttpStatus;
import io.grpc.StatusRuntimeException;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ApplicationExceptionHandler {

  private final ExceptionMessage exceptionMessage;

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorMessage> handleConstraintViolation(
      @Nonnull final HttpServletRequest request,
      @Nonnull final Exception exception) {
    log.error("Validation failed: {}", exception.getMessage());
    final var message = ErrorMessage.builder()
        .httpStatus(HttpStatus.BAD_REQUEST.value())
        .date(new Date())
        .url(request.getRequestURI())
        .description(exceptionMessage.getBadRequestMessage())
        .build();
    return ResponseEntity.status(message.getHttpStatus()).body(message);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorMessage> handleIllegalArgument(
      @Nonnull final HttpServletRequest request,
      @Nonnull final Exception exception) {
    log.error("Cant create entity: {}", exception.getMessage());
    final var message = ErrorMessage.builder()
        .httpStatus(HttpStatus.BAD_REQUEST.value())
        .date(new Date())
        .url(request.getRequestURI())
        .description(exceptionMessage.getBadRequestMessage())
        .build();
    return ResponseEntity.status(message.getHttpStatus()).body(message);
  }

  @ExceptionHandler(StatusRuntimeException.class)
  public ResponseEntity<ErrorMessage> handleWhenBookNotFound(
      @Nonnull final HttpServletRequest request,
      @Nonnull final StatusRuntimeException exception) {
    log.error("Server exception is encountered: {}", exception.getMessage());
    var httpStatus =
        ServerErrorHttpStatus.valueOf(exception.getStatus().getCode().name()).getHttpStatus();
    final var message = ErrorMessage.builder()
        .httpStatus(httpStatus.value())
        .date(new Date())
        .url(request.getRequestURI())
        .description(exception.getMessage())
        .build();
    return ResponseEntity.status(message.getHttpStatus()).body(message);
  }
}
