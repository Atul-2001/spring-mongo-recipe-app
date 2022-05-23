package com.signature.recipe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.signature.recipe.data.UnitOfMeasureDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class UnitOfMeasure {

  @Id
  private String id;
  private String description;

  public UnitOfMeasure(String description) {
    this.description = description;
  }

  @JsonIgnore
  public UnitOfMeasureDTO getDTO() {
    return new UnitOfMeasureDTO(id, description);
  }
}