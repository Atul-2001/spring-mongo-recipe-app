package com.signature.recipe.service;

import com.signature.recipe.exceptions.NotFoundException;
import com.signature.recipe.model.Recipe;
import com.signature.recipe.repository.RecipeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

class RecipeServiceTest {

  RecipeService recipeService;

  @Mock
  RecipeRepository recipeRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    recipeService = new RecipeService(recipeRepository);
  }

  @Test
  void getAllRecipes() {
    Recipe recipe = new Recipe();
    HashSet<Recipe> recipeData = new HashSet<>();
    recipeData.add(recipe);

    when(recipeRepository.findAll()).thenReturn(recipeData);

    List<Recipe> recipes = recipeService.getAllRecipes();

    assertEquals(recipes.size(), 1);

    verify(recipeRepository, times(1)).findAll();
  }

  @Test
  void getById() {
    when(recipeRepository.findById(anyString()))
            .thenReturn(Optional.of(Recipe.builder().id("1L").build()));

    Recipe recipeReturned = recipeService.getById("1L");

    assertNotNull("Null recipe returned", recipeReturned);
    verify(recipeRepository, times(1)).findById(anyString());
    verify(recipeRepository, never()).findAll();
  }


  @Test
  void delete() {
    Recipe recipe = Recipe.builder().id("1L").build();

    recipeService.delete(recipe);

    verify(recipeRepository, times(1)).delete(any());
  }

  @Test
  void deleteById() {
    recipeService.deleteById("1L");

    verify(recipeRepository, times(1)).deleteById(anyString());
  }

  @Test
  public void getRecipeByIdTestNotFound() {
    Optional<Recipe> recipeOptional = Optional.empty();

    when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

    Assertions.assertThrows(NotFoundException.class, () -> {
      Recipe recipeReturned = recipeService.getById("1L");
    });
  }
}