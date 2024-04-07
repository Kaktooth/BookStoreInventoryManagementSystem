package com.kaktooth.bookstore.inventory_management.server.model.dto;

import com.kaktooth.bookstore.inventory_management.server.common.ApplicationConstants;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookDto {

  @NotBlank
  @NotEmpty
  @Size(min = ApplicationConstants.MIN_TITLE_SIZE, max = ApplicationConstants.MAX_TITLE_SIZE)
  private String title;

  @NotBlank
  @NotEmpty
  @Size(min = ApplicationConstants.MIN_AUTHOR_FULLNAME_SIZE, max = ApplicationConstants.MAX_AUTHOR_FULLNAME_SIZE)
  private String author;

  @NotBlank
  @NotEmpty
  private String genre;

  @NotBlank
  @NotEmpty
  @Column(length = 13)
  private String isbn;

  private Integer quantity;
}
