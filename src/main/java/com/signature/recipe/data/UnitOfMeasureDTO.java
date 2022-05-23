package com.signature.recipe.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.signature.recipe.model.UnitOfMeasure;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UnitOfMeasureDTO {

  @Id
  private String id;
  private String description;

  @JsonIgnore
  public UnitOfMeasure getModel() {
    return UnitOfMeasure.builder().id(id)
            .description(description).build();
  }
}