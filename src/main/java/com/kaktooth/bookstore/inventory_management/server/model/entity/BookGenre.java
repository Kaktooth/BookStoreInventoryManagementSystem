package com.kaktooth.bookstore.inventory_management.server.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "book_genre")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookGenre {
  @Id
  @ToString.Exclude
  private Integer id;

  @NotBlank
  @NotEmpty
  private String name;
}
