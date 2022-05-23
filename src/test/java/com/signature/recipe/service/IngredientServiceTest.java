package com.signature.recipe.service;

import com.signature.recipe.data.IngredientDTO;
import com.signature.recipe.data.UnitOfMeasureDTO;
import com.signature.recipe.model.Ingredient;
import com.signature.recipe.model.Recipe;
import com.signature.recipe.repository.RecipeRepository;
import com.signature.recipe.repository.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

class IngredientServiceTest {

  @Mock
  public RecipeRepository recipeRepository;

  @Mock
  public UnitOfMeasureRepository unitOfMeasureRepository;

  private IngredientService ingredientService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    this.ingredientService = new IngredientService(recipeRepository, unitOfMeasureRepository);
  }

  @Test
  void getByRecipeAndId() {
    //given
    Recipe recipe = new Recipe();
    recipe.setId("1111-1111-1111-1111");

    Ingredient ingredient1 = new Ingredient();
    ingredient1.setId("1111-2222-3333-4444");

    Ingredient ingredient2 = new Ingredient();
    ingredient2.setId("4444-3333-2222-1111");

    Ingredient ingredient3 = new Ingredient();
    ingredient3.setId("1111-2222-4444-3333");

    recipe.addIngredient(ingredient1);
    recipe.addIngredient(ingredient2);
    recipe.addIngredient(ingredient3);

    when(recipeRepository.findById(anyString())).thenReturn(Optional.of(recipe));

    //then
    IngredientDTO ingredient = ingredientService.getByRecipeAndId("1111-1111-1111-1111", "1111-2222-4444-3333");

    //when
    assertNotNull("Ingredient must not be null", ingredient);

    assertEquals("1111-2222-4444-3333", ingredient.getId());
    assertEquals("1111-1111-1111-1111", ingredient.getRecipeId());

    verify(recipeRepository, times(1)).findById(anyString());
  }


  @Test
  void saveOrUpdate() {
    //given
    IngredientDTO ingredientDTO = new IngredientDTO();
    ingredientDTO.setId("3L");
    ingredientDTO.setRecipeId("2L");
    ingredientDTO.setUnitOfMeasure(new UnitOfMeasureDTO());

    Optional<Recipe> recipeOptional = Optional.of(new Recipe());

    Recipe savedRecipe = new Recipe();
    savedRecipe.addIngredient(new Ingredient());
    savedRecipe.getIngredients().iterator().next().setId("3L");

    when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);
    when(recipeRepository.save(any())).thenReturn(savedRecipe);

    //when
    IngredientDTO savedCommand = ingredientService.saveOrUpdate(ingredientDTO);

    //then
    assertEquals("3L", savedCommand.getId());
    verify(recipeRepository, times(1)).findById(anyString());
    verify(recipeRepository, times(1)).save(any(Recipe.class));
  }

  @Test
  void testGetByRecipeAndId() {
    //given
    Recipe recipe = new Recipe();
    recipe.setId("1L");

    Ingredient ingredient1 = new Ingredient();
    ingredient1.setId("1L");

    Ingredient ingredient2 = new Ingredient();
    ingredient2.setId("1L");

    Ingredient ingredient3 = new Ingredient();
    ingredient3.setId("3L");

    recipe.addIngredient(ingredient1);
    recipe.addIngredient(ingredient2);
    recipe.addIngredient(ingredient3);
    Optional<Recipe> recipeOptional = Optional.of(recipe);

    when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

    //then
    IngredientDTO ingredient = ingredientService.getByRecipeAndId("1L", "3L");

    //when
    assertEquals("3L", ingredient.getId());
    assertEquals("1L", ingredient.getRecipeId());
    verify(recipeRepository, times(1)).findById(anyString());
  }

  @Test
  void deleteByRecipeAndId() {
    //given
    Recipe recipe = new Recipe();
    recipe.addIngredient(Ingredient.builder().id("3L").build());
    Optional<Recipe> recipeOptional = Optional.of(recipe);

    //when
    when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

    ingredientService.deleteByRecipeAndId("1L", "3L");

    //then
    verify(recipeRepository, times(1)).findById(anyString());
    verify(recipeRepository, times(1)).save(any(Recipe.class));
  }
}