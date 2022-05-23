package com.signature.recipe.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.signature.recipe.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

  @Id
  private String id;
  private String description;

  @JsonIgnore
  public Category getModel() {
    return Category.builder().id(id)
            .description(description).build();
  }
}