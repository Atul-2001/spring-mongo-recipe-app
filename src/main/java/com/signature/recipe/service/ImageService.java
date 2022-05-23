package com.signature.recipe.service;

import com.signature.recipe.model.Recipe;
import com.signature.recipe.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public class ImageService {

  private final RecipeRepository recipeRepository;

  public ImageService(RecipeRepository recipeRepository) {
    this.recipeRepository = recipeRepository;
  }

  @Transactional
  public void saveImageFile(String recipeId, MultipartFile multipartFile) {
    try {
      final Optional<Recipe> recipe = recipeRepository.findById(recipeId);

      if (recipe.isEmpty()) {
        log.info("Recipe not found for id : {}", recipeId);
        return;
      }

      recipe.get().setImage(multipartFile.getBytes());

      recipeRepository.save(recipe.get());
    } catch (final IOException ex) {
      log.error("Failed to save image file", ex);
    }
  }
}