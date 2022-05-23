package com.signature.recipe.controller;

import com.signature.recipe.data.RecipeDTO;
import com.signature.recipe.model.Category;
import com.signature.recipe.model.Ingredient;
import com.signature.recipe.model.Recipe;
import com.signature.recipe.service.RecipeService;
import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Log4j2
@Controller
@RequestMapping("/recipe")
public class RecipeController {

  private static final String RECIPE_FORM_URL = "recipe/form";

  private final RecipeService recipeService;

  public RecipeController(RecipeService recipeService) {
    this.recipeService = recipeService;
  }

  @GetMapping("/new")
  public String createRecipe(final Model model) {
    model.addAttribute("recipe", new RecipeDTO());
    return RECIPE_FORM_URL;
  }

  @GetMapping("/{id}/show")
  public String showById(@PathVariable String id, final Model model) {
    log.info("Get recipe for id " + id);
    model.addAttribute("recipe", recipeService.getById(id));
    return "recipe/show";
  }

  @GetMapping("/{id}/update")
  public String updateRecipe(@PathVariable String id, final Model model) {
    model.addAttribute("recipe", recipeService.getById(id).getDTO());
    return RECIPE_FORM_URL;
  }

  @PostMapping
  public String addOrUpdate(@Valid @ModelAttribute("recipe") final RecipeDTO recipeDTO, BindingResult bindingResult) {
    final Recipe recipe = recipeService.getById(recipeDTO.getId(), true);
    if (recipe != null) {
      recipeDTO.setCategories(recipe.getCategories().stream()
              .map(Category::getDTO).collect(Collectors.toSet()));
      recipeDTO.setIngredients(recipe.getIngredients().stream()
              .map(Ingredient::getDTO).collect(Collectors.toSet()));
      recipeDTO.setImage(recipe.getImage());
      if (recipe.getNote().getDescription()
              .equals(recipeDTO.getNotes().getDescription())) {
        recipeDTO.getNotes().setId(ObjectId.get().toString());
      } else {
        recipeDTO.getNotes().setId(recipe.getNote().getId());
      }
    }

    if (bindingResult.hasErrors()) {
      bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));

      return RECIPE_FORM_URL;
    }

    return "redirect:/recipe/".concat(recipeService.save(recipeDTO).getId()).concat("/show");
  }

  @GetMapping("/{id}/delete")
  public String deleteRecipe(@PathVariable String id) {
    recipeService.deleteById(id);
    return "redirect:/";
  }
}