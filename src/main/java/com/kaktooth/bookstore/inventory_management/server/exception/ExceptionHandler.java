package com.kaktooth.bookstore.inventory_management.server.exception;

import com.google.protobuf.Any;
import com.google.rpc.Code;
import com.google.rpc.ErrorInfo;
import com.google.rpc.Status;
import com.kaktooth.bookstore.inventory_management.protobuf.Book;
import com.kaktooth.bookstore.inventory_management.protobuf.BookRetrieveOption;
import com.kaktooth.bookstore.inventory_management.protobuf.RetrieveRequest;
import com.kaktooth.bookstore.inventory_management.server.common.ApplicationConstants;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.StatusProto;
import java.util.Map;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ExceptionHandler {

  private static final Map<BookRetrieveOption, String> RETRIEVED_BY = Map.of(
      BookRetrieveOption.BY_ISBN, "by isbn",
      BookRetrieveOption.BY_TITLE, "by title",
      BookRetrieveOption.BY_AUTHOR, "by author",
      BookRetrieveOption.BY_GENRE, "by genre"
  );

  public StatusRuntimeException getBookAlreadyExistsException(Book request) {
    Status status = Status.newBuilder()
        .setCode(Code.ALREADY_EXISTS.getNumber())
        .setMessage("Cant create book with title or isbn that already exists. Book title: " +
            request.getTitle() + ", isbn: " + request.getIsbn())
        .addDetails(Any.pack(ErrorInfo.newBuilder()
            .setReason("Book already exists.")
            .setDomain(ApplicationConstants.BOOK_STORE_INVENTORY_SYSTEM_SERVER_DOMAIN)
            .putMetadata("addBook", request.toString())
            .build()
        )).build();
    return StatusProto.toStatusRuntimeException(status);
  }

  public StatusRuntimeException getInvalidBookParametersException(Book request) {
    Status status = Status.newBuilder()
        .setCode(Code.INVALID_ARGUMENT.getNumber())
        .setMessage("Book cant be updated because book parameters is invalid.")
        .addDetails(Any.pack(ErrorInfo.newBuilder()
            .setReason("Book parameters is invalid")
            .setDomain(ApplicationConstants.BOOK_STORE_INVENTORY_SYSTEM_SERVER_DOMAIN)
            .putMetadata("updateBook", request.toString())
            .build()
        )).build();
    return StatusProto.toStatusRuntimeException(status);
  }

  public StatusRuntimeException getBookNotFoundException(RetrieveRequest request) {
    Status status = Status.newBuilder()
        .setCode(Code.NOT_FOUND.getNumber())
        .setMessage(
            "Cant retrieve any book/books " + RETRIEVED_BY.get(request.getBookRetrieveOption()) +
                " " + request.getRequestValue())
        .addDetails(Any.pack(ErrorInfo.newBuilder()
            .setReason(
                "Requested value for retrieving option is correct but book/books not exists.")
            .setDomain(ApplicationConstants.BOOK_STORE_INVENTORY_SYSTEM_SERVER_DOMAIN)
            .putMetadata("retrieveBooks" + request.getBookRetrieveOption(),
                request.getRequestValue())
            .build()
        )).build();
    return StatusProto.toStatusRuntimeException(status);
  }

  public StatusRuntimeException getInvalidParameterException(RetrieveRequest request) {
    Status status = Status.newBuilder()
        .setCode(Code.INVALID_ARGUMENT.getNumber())
        .setMessage(
            "Cant retrieve book/books because of invalid parameter: " + request.getRequestValue())
        .addDetails(Any.pack(ErrorInfo.newBuilder()
            .setReason("Invalid requested value for retrieving option.")
            .setDomain(ApplicationConstants.BOOK_STORE_INVENTORY_SYSTEM_SERVER_DOMAIN)
            .putMetadata("retrieveBooks", request.getRequestValue())
            .build()
        )).build();
    return StatusProto.toStatusRuntimeException(status);
  }

  public StatusRuntimeException getInvalidParameterException(Book request) {
    Status status = Status.newBuilder()
        .setCode(Code.INVALID_ARGUMENT.getNumber())
        .setMessage("Cant delete book because book isbn parameter is invalid: " + request.getIsbn())
        .addDetails(Any.pack(ErrorInfo.newBuilder()
            .setReason("Book isbn parameter is invalid.")
            .setDomain(ApplicationConstants.BOOK_STORE_INVENTORY_SYSTEM_SERVER_DOMAIN)
            .putMetadata("deleteBook", request.toString())
            .build()
        )).build();
    return StatusProto.toStatusRuntimeException(status);
  }
}
