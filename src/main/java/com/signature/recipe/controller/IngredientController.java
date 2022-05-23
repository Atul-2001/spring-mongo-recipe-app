package com.signature.recipe.controller;

import com.signature.recipe.data.IngredientDTO;
import com.signature.recipe.data.UnitOfMeasureDTO;
import com.signature.recipe.exceptions.NotFoundException;
import com.signature.recipe.model.Recipe;
import com.signature.recipe.service.IngredientService;
import com.signature.recipe.service.RecipeService;
import com.signature.recipe.service.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/recipe/{recipeId}")
public class IngredientController {

  private final RecipeService recipeService;
  private final IngredientService ingredientService;

  private final UnitOfMeasureService unitOfMeasureService;

  public IngredientController(final RecipeService recipeService,
                              final IngredientService ingredientService,
                              final UnitOfMeasureService unitOfMeasureService) {
    this.recipeService = recipeService;
    this.ingredientService = ingredientService;
    this.unitOfMeasureService = unitOfMeasureService;
  }

  @GetMapping("/ingredient/new")
  public String newRecipe(@PathVariable String recipeId, Model model) {
    //make sure we have a good id value
    Recipe recipe = recipeService.getById(recipeId);

    if (Objects.isNull(recipe)) {
      throw new NotFoundException("Recipe not found for id : " + recipeId);
    }

    //need to return parent id for hidden form property
    IngredientDTO ingredientDTO = new IngredientDTO();
    ingredientDTO.setRecipeId(recipeId);
    ingredientDTO.setUnitOfMeasure(new UnitOfMeasureDTO());

    model.addAttribute("ingredient", ingredientDTO);
    model.addAttribute("unitOfMeasures", unitOfMeasureService.getAll());

    return "/recipe/ingredient/form";
  }

  @GetMapping("/ingredients")
  public String listIngredients(@PathVariable String recipeId, Model model) {
    log.debug("Getting ingredient list for recipe id: " + recipeId);
    final Recipe recipe = recipeService.getById(recipeId);
    model.addAttribute("recipe", recipe.getDTO());
    return "/recipe/ingredient/index";
  }

  @GetMapping("/ingredient/{ingredientId}/show")
  public String getIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
    log.debug("Getting ingredient for id {} of recipe id {} ", ingredientId, recipeId);
    final IngredientDTO ingredient = ingredientService.getByRecipeAndId(recipeId, ingredientId);
    if (ingredient == null) {
      return "/recipe/ingredient/index";
    } else {
      model.addAttribute("ingredient", ingredient);
      return "/recipe/ingredient/show";
    }
  }

  @GetMapping("/ingredient/{id}/update")
  public String updateRecipeIngredient(@PathVariable String recipeId,
                                       @PathVariable String id,
                                       final Model model) {
    model.addAttribute("ingredient", ingredientService.getByRecipeAndId(recipeId, id));

    model.addAttribute("unitOfMeasures", unitOfMeasureService.getAll());
    return "/recipe/ingredient/form";
  }

  @PostMapping("ingredient")
  public String saveOrUpdate(@ModelAttribute IngredientDTO ingredient) {
    IngredientDTO ingredientDTO = ingredientService.saveOrUpdate(ingredient);

    log.debug("saved ingredient id:" + ingredientDTO.getId());

    return String.format("redirect:/recipe/%s/ingredient/%s/show",
            ingredientDTO.getRecipeId(), ingredientDTO.getId());
  }

  @GetMapping("/ingredient/{id}/delete")
  public String deleteIngredient(@PathVariable String recipeId, @PathVariable String id) {
    log.debug("deleting ingredient id:" + id);

    ingredientService.deleteByRecipeAndId(recipeId, id);

    return String.format("redirect:/recipe/%s/ingredients", recipeId);
  }
}