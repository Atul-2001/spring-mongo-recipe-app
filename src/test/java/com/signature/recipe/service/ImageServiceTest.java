package com.signature.recipe.service;

import com.signature.recipe.model.Recipe;
import com.signature.recipe.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class ImageServiceTest {

  public ImageService imageService;

  @Mock
  public RecipeRepository recipeRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    this.imageService = new ImageService(recipeRepository);
  }

  @Test
  void saveImageFile() throws IOException {
    //given
    final String id = UUID.randomUUID().toString();
    final MultipartFile multipartFile = new MockMultipartFile("file", "testing.txt",
            "text/plain", "Spring Framework Guru".getBytes());

    final Recipe recipe = Recipe.builder().id(id).build();
    final Optional<Recipe> recipeOptional = Optional.of(recipe);

    when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

    ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

    //when
    imageService.saveImageFile(id, multipartFile);

    //then
    verify(recipeRepository, times(1)).save(argumentCaptor.capture());
    Recipe savedRecipe = argumentCaptor.getValue();
    assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);
  }
}