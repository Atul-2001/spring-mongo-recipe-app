package com.signature.recipe.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.signature.recipe.model.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDTO {

  @Id
  private String id;
  private String recipeId;
  private BigDecimal amount;
  private String description;
  private UnitOfMeasureDTO unitOfMeasure;

  public IngredientDTO(String id, BigDecimal amount, String description,
                       UnitOfMeasureDTO unitOfMeasure) {
    this.id = id;
    this.amount = amount;
    this.description = description;
    this.unitOfMeasure = unitOfMeasure;
  }

  public IngredientDTO setRecipeId(String recipeId) {
    this.recipeId = recipeId;
    return this;
  }

  private boolean isUnitPresent() {
    return !(unitOfMeasure == null || StringUtils.isBlank(unitOfMeasure.getDescription()));
  }

  public String getInfo() {
    if (amount == null) {
      return this.description;
    } else {
      return !isUnitPresent() ? amount.toString() + ' ' + this.description :
              amount.toString() + ' ' + unitOfMeasure.getDescription() + ' ' + this.description;
    }
  }

  @JsonIgnore
  public Ingredient getModel() {
    if (StringUtils.isBlank(id))
      this.id = ObjectId.get().toString();
    return Ingredient.builder().id(id)
            .amount(amount).description(description)
            .unit(unitOfMeasure.getModel()).build();
  }
}