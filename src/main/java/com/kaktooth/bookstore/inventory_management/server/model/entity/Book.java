package com.kaktooth.bookstore.inventory_management.server.model.entity;

import static com.kaktooth.bookstore.inventory_management.server.common.ApplicationConstants.ISBN_LENGTH;

import com.kaktooth.bookstore.inventory_management.server.common.ApplicationConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "book")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @ToString.Exclude
  private UUID id;

  @NotBlank
  @NotEmpty
  @Size(min = ApplicationConstants.MIN_TITLE_SIZE, max = ApplicationConstants.MAX_TITLE_SIZE)
  private String title;

  @NotBlank
  @NotEmpty
  @Size(min = ApplicationConstants.MIN_AUTHOR_FULLNAME_SIZE, max = ApplicationConstants.MAX_AUTHOR_FULLNAME_SIZE)
  private String author;

  @ManyToOne
  @JoinColumn(name = "genre_id")
  private BookGenre genre;

  @NotBlank
  @NotEmpty
  @Size(min = ISBN_LENGTH, max = ISBN_LENGTH)
  private String isbn;

  private Integer quantity;
}
