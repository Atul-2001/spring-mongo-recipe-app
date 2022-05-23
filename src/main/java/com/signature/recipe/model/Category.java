package com.signature.recipe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.signature.recipe.data.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Category {

  @Id
  private String id;

  @NonNull
  private String description;

  @DBRef
  @Builder.Default
  @ToString.Exclude
  private Set<Recipe> recipes = new HashSet<>();

  public Category(@NonNull String description) {
    this.description = description;
  }

  @JsonIgnore
  public CategoryDTO getDTO() {
    return new CategoryDTO(id, description);
  }
}