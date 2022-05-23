package com.signature.recipe.controller;

import com.signature.recipe.service.RecipeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
public class IndexController {

  private final RecipeService recipeService;

  public IndexController(RecipeService recipeService) {
    log.trace("Loading Index controller...");
    this.recipeService = recipeService;
  }

  @GetMapping({"", "/", "/index", "/index.html"})
  public String home(Model model) {
    log.info("Serving /home from Home Controller");
    model.addAttribute("recipes", recipeService.getAllRecipes());
    return "index";
  }
}