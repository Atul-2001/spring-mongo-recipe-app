package com.signature.recipe.service;

import com.signature.recipe.data.RecipeDTO;
import com.signature.recipe.exceptions.NotFoundException;
import com.signature.recipe.model.Recipe;
import com.signature.recipe.repository.RecipeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Log4j2
@Service
public class RecipeService {

  private final RecipeRepository recipeRepository;

  public RecipeService(RecipeRepository recipeRepository) {
    log.debug("Loading Recipe Service...");
    this.recipeRepository = recipeRepository;
  }

  public Recipe save(RecipeDTO recipeDTO) {
    return recipeRepository.save(recipeDTO.getModel());
  }

  public List<Recipe> getAllRecipes() {
    log.debug("Getting list of recipes...");
    return StreamSupport.stream(recipeRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
  }

  public Recipe getById(final String id) {
    log.debug("Getting recipe for id : " + id);
    Optional<Recipe> recipe = recipeRepository.findById(id);
    if (recipe.isEmpty()) {
      throw new NotFoundException("Recipe Not Found for id : " + id);
    }
    return recipe.orElse(null);
  }

  public Recipe getById(final String id, Boolean isNew) {
    log.debug("Getting recipe for id : " + id);
    Optional<Recipe> recipe = recipeRepository.findById(id);
    if (recipe.isEmpty() && !isNew) {
      throw new NotFoundException("Recipe Not Found for id : " + id);
    }
    return recipe.orElse(null);
  }

  public void delete(final Recipe recipe) {
    log.debug("Deleting recipe using recipe model");
    recipeRepository.delete(recipe);
  }

  public void deleteById(final String id) {
    log.debug("Deleting recipe for id : " + id);
    recipeRepository.deleteById(id);
  }
}