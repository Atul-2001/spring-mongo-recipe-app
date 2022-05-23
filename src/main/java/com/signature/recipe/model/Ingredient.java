package com.signature.recipe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.signature.recipe.data.IngredientDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {

  private String id;
  private BigDecimal amount;
  private String description;

  @DBRef
  private UnitOfMeasure unit;

  public Ingredient(BigDecimal amount, UnitOfMeasure unit, String description) {
    this.id = ObjectId.get().toString();
    this.unit = unit;
    this.amount = amount;
    this.description = description;
  }

  private boolean isUnitPresent() {
    return !(unit == null || StringUtils.isBlank(unit.getDescription()));
  }

  public String getInfo() {
    if (amount == null) {
      return this.description;
    } else {
      return !isUnitPresent() ? amount.toString() + ' ' + this.description :
              amount.toString() + ' ' + unit.getDescription() + ' ' + this.description;
    }
  }

  @JsonIgnore
  public IngredientDTO getDTO() {
    return new IngredientDTO(id, amount, description, unit == null ? null : unit.getDTO());
  }
}