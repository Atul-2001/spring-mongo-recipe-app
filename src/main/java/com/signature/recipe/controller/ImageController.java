package com.signature.recipe.controller;

import com.signature.recipe.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
public class ImageController {

  private final ImageService imageService;

  public ImageController(ImageService imageService) {
    this.imageService = imageService;
  }

  @PostMapping("/recipe/{recipeId}/image")
  public String uploadImage(@PathVariable String recipeId,
                            @RequestParam("file") MultipartFile file) {
    log.info("Uploading image file for recipe id : {}", recipeId);

    imageService.saveImageFile(recipeId, file);

    return String.format("redirect:/recipe/%s/show", recipeId);
  }
}