package com.signature.recipe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.signature.recipe.data.IngredientDTO;
import com.signature.recipe.data.RecipeDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Base64;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

  @Id
  private String id;
  private String description;
  private Byte[] image;
  private Integer prepTime;
  private Integer cookTime;
  private Integer servings;
  private String source;
  private String url;
  private Integer rating;
  private Difficulty difficulty;

  @DBRef
  @Builder.Default
  @ToString.Exclude
  private Set<Category> categories = new HashSet<>();

  @Builder.Default
  @ToString.Exclude
  private Set<Ingredient> ingredients = new HashSet<>();

  private String directions;

  private Note note;

  public byte[] getImage() {
    if (Objects.isNull(image)) {
      return new byte[0];
    }
    byte[] bytes = new byte[image.length];
    for (int i = 0; i < image.length; i++) {
      bytes[i] = image[i];
    }
    return bytes;
  }

  public void setImage(byte[] image) {
    if (Objects.nonNull(image)) {
      Byte[] bytes = new Byte[image.length];
      for (int i = 0; i < image.length; i++) {
        bytes[i] = image[i];
      }
      this.image = bytes;
    }
  }

  public void setImage(Byte[] image) {
    this.image = image;
  }

  public String getBase64Image() {
    return "data:image/jpeg;base64,".concat(Base64.getMimeEncoder().encodeToString(getImage()));
  }

  public Recipe addCategory(Category category) {
    category.getRecipes().add(this);
    this.categories.add(category);
    return this;
  }

  public Recipe addIngredient(Ingredient ingredient) {
    this.ingredients.add(ingredient);
    return this;
  }

  public void setNote(Note note) {
    if (note != null) {
      this.note = note;
    }
  }

  @JsonIgnore
  public RecipeDTO getDTO() {
    final RecipeDTO recipeDTO = new RecipeDTO();
    recipeDTO.setId(id);
    recipeDTO.setUrl(url);
    recipeDTO.setImage(image);
    recipeDTO.setSource(source);
    recipeDTO.setPrepTime(prepTime);
    recipeDTO.setCookTime(cookTime);
    recipeDTO.setServings(servings);
    recipeDTO.setDifficulty(difficulty);
    recipeDTO.setDirections(directions);
    recipeDTO.setDescription(description);
    recipeDTO.setNotes(note == null ? null : note.getDTO());
    recipeDTO.setCategories(categories.stream().map(Category::getDTO)
            .collect(Collectors.toSet()));
    recipeDTO.setIngredients(ingredients.stream().map(Ingredient::getDTO)
            .sorted(Comparator.comparing(IngredientDTO::getId))
            .collect(Collectors.toCollection(LinkedHashSet::new)));
    return recipeDTO;
  }

  public static class RecipeBuilder {

    public RecipeBuilder image(byte[] image) {
      if (Objects.nonNull(image)) {
        Byte[] bytes = new Byte[image.length];
        for (int i = 0; i < image.length; i++) {
          bytes[i] = image[i];
        }
        this.image = bytes;
      }
      return this;
    }

    public RecipeBuilder image(Byte[] image) {
      this.image = image;
      return this;
    }
  }
}