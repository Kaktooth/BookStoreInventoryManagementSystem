package com.kaktooth.bookstore.inventory_management.client.handler;

import java.util.Date;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ErrorMessage {
  int httpStatus;
  Date date;
  String description;
  String url;
}
