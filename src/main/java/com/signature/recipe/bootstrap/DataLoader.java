package com.signature.recipe.bootstrap;

import com.signature.recipe.model.Category;
import com.signature.recipe.model.Difficulty;
import com.signature.recipe.model.Ingredient;
import com.signature.recipe.model.Note;
import com.signature.recipe.model.Recipe;
import com.signature.recipe.model.UnitOfMeasure;
import com.signature.recipe.repository.CategoryRepository;
import com.signature.recipe.repository.RecipeRepository;
import com.signature.recipe.repository.UnitOfMeasureRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Component
@Profile("default")
@AllArgsConstructor
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

  private final RecipeRepository recipeRepository;
  private final CategoryRepository categoryRepository;
  private final UnitOfMeasureRepository unitOfMeasureRepository;

  @Override
  @Transactional
  public void onApplicationEvent(ContextRefreshedEvent event) {
    log.debug("START loading bootstrap data...");
    loadCategories();
    loadUnitOfMeasures();
    recipeRepository.saveAll(getRecipes());
    log.debug("FINISH loading bootstrap data...");
  }

  private void loadCategories() {
    categoryRepository.save(new Category("Italian"));
    categoryRepository.save(new Category("Mexican"));
    categoryRepository.save(new Category("American"));
    categoryRepository.save(new Category("Fast Food"));
  }

  private void loadUnitOfMeasures() {
    unitOfMeasureRepository.save(new UnitOfMeasure(""));
    unitOfMeasureRepository.save(new UnitOfMeasure("Cup"));
    unitOfMeasureRepository.save(new UnitOfMeasure("Cups"));
    unitOfMeasureRepository.save(new UnitOfMeasure("Dash"));
    unitOfMeasureRepository.save(new UnitOfMeasure("Each"));
    unitOfMeasureRepository.save(new UnitOfMeasure("Pint"));
    unitOfMeasureRepository.save(new UnitOfMeasure("Ripe"));
    unitOfMeasureRepository.save(new UnitOfMeasure("Small"));
    unitOfMeasureRepository.save(new UnitOfMeasure("Ounce"));
    unitOfMeasureRepository.save(new UnitOfMeasure("Pinch"));
    unitOfMeasureRepository.save(new UnitOfMeasure("Medium"));
    unitOfMeasureRepository.save(new UnitOfMeasure("Teaspoon"));
    unitOfMeasureRepository.save(new UnitOfMeasure("Tablespoon"));
  }

  private List<Recipe> getRecipes() {
    List<Recipe> recipes = new ArrayList<>();

    Optional<Category> americanDishOptional = categoryRepository.findByDescription("American");
    if (americanDishOptional.isEmpty()) {
      throw new RuntimeException("Category not found!");
    }

    Optional<Category> mexicanDishOptional = categoryRepository.findByDescription("Mexican");
    if (mexicanDishOptional.isEmpty()) {
      throw new RuntimeException("Category not found!");
    }

    Category americanDish = americanDishOptional.get();
    Category mexicanDish = mexicanDishOptional.get();

    log.info("Fetched all the categories...");

    Optional<UnitOfMeasure> noneOptional = unitOfMeasureRepository.findByDescription("");
    if (noneOptional.isEmpty()) {
      throw new RuntimeException("Unit of Measure not found!");
    }

    Optional<UnitOfMeasure> smallOptional = unitOfMeasureRepository.findByDescription("Small");
    if (smallOptional.isEmpty()) {
      throw new RuntimeException("Unit of Measure not found!");
    }

    Optional<UnitOfMeasure> mediumOptional = unitOfMeasureRepository.findByDescription("Medium");
    if (mediumOptional.isEmpty()) {
      throw new RuntimeException("Unit of Measure not found!");
    }

    Optional<UnitOfMeasure> ripeOptional = unitOfMeasureRepository.findByDescription("Ripe");
    if (ripeOptional.isEmpty()) {
      throw new RuntimeException("Unit of Measure not found!");
    }

    Optional<UnitOfMeasure> pintOptional = unitOfMeasureRepository.findByDescription("Pint");
    if (pintOptional.isEmpty()) {
      throw new RuntimeException("Unit of Measure not found!");
    }

    Optional<UnitOfMeasure> cupOptional = unitOfMeasureRepository.findByDescription("Cup");
    if (cupOptional.isEmpty()) {
      throw new RuntimeException("Unit of Measure not found!");
    }

    Optional<UnitOfMeasure> cupsOptional = unitOfMeasureRepository.findByDescription("Cups");
    if (cupsOptional.isEmpty()) {
      throw new RuntimeException("Unit of Measure not found!");
    }

    Optional<UnitOfMeasure> teaspoonOptional = unitOfMeasureRepository.findByDescription("Teaspoon");
    if (teaspoonOptional.isEmpty()) {
      throw new RuntimeException("Unit of Measure not found!");
    }

    Optional<UnitOfMeasure> tablespoonOptional = unitOfMeasureRepository.findByDescription("Tablespoon");
    if (tablespoonOptional.isEmpty()) {
      throw new RuntimeException("Unit of Measure not found!");
    }

    log.info("Fetched all the unit of measures...");

    UnitOfMeasure cup = cupOptional.get();
    UnitOfMeasure cups = cupsOptional.get();
    UnitOfMeasure none = noneOptional.get();
    UnitOfMeasure pint = pintOptional.get();
    UnitOfMeasure ripe = ripeOptional.get();
    UnitOfMeasure small = smallOptional.get();
    UnitOfMeasure medium = mediumOptional.get();
    UnitOfMeasure teaspoon = teaspoonOptional.get();
    UnitOfMeasure tablespoon = tablespoonOptional.get();

    Recipe recipe = new Recipe();
    recipe.setDescription("Guacamole: A Classic Mexican Dish");
    recipe.setPrepTime(10);
    recipe.setCookTime(0);
    recipe.setServings(4);
    recipe.setSource("Simply Recipes");
    recipe.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
    recipe.setRating(4);
    recipe.setDifficulty(Difficulty.MODERATE);
    recipe.addCategory(mexicanDish).addCategory(americanDish);
    recipe.setDirections("Firstly Cut the avocado and then mash the avocado flesh after that add remaining ingredients to taste. Serve immediately");

    recipe
            .addIngredient(new Ingredient(new BigDecimal(2), ripe, "avocados"))
            .addIngredient(new Ingredient(BigDecimal.valueOf(1.0 / 4.0), teaspoon, "salt, plus more to taste"))
            .addIngredient(new Ingredient(new BigDecimal(1), tablespoon, "fresh lime or lemon juice"))
            .addIngredient(new Ingredient(new BigDecimal(4), tablespoon, "minced red onion or thinly sliced green onion"))
            .addIngredient(new Ingredient(new BigDecimal(2), none, "serrano (or jalapeno) chili's, stems and seeds removed, minced"))
            .addIngredient(new Ingredient(new BigDecimal(2), tablespoon, "cilantro (leaves and tender stems), finely chopped"))
            .addIngredient(new Ingredient(null, none, "Pinch freshly ground black pepper"))
            .addIngredient(new Ingredient(BigDecimal.valueOf(1.0 / 2.0), ripe, "tomato, chopped (optional)"))
            .addIngredient(new Ingredient(null, none, "Red radish or jicama slices for garnish (optional)"))
            .addIngredient(new Ingredient(null, none, "Tortilla chips, to serve"));

    recipe.setNote(new Note("Be careful handling chili's! " +
            "If using, it's best to wear food-safe gloves. " +
            "If no gloves are available, wash your hands thoroughly after handling, " +
            "and do not touch your eyes or the area near your eyes for several hours afterwards."));
    recipes.add(recipe);

    log.info("Added recipe >> Guacamole: A Classic Mexican Dish");

    recipe = new Recipe();
    recipe.setDescription("Spicy Grilled Chicken Tacos");
    recipe.setPrepTime(20);
    recipe.setCookTime(15);
    recipe.setServings(6);
    recipe.setSource("Simply Recipes");
    recipe.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
    recipe.setRating(3);
    recipe.setDifficulty(Difficulty.HARD);
    recipe.addCategory(americanDish);
    recipe.setDirections("Prepare a gas or charcoal grill for medium-high, direct heat >>>" +
            " Make the marinade and coat the chicken >>> Grill the chicken >>> Warm the tortillas " +
            ">>> Assemble the tacos");

    recipe.addIngredient(new Ingredient(new BigDecimal(2), tablespoon, "anchor chili powder"))
            .addIngredient(new Ingredient(new BigDecimal(1), teaspoon, "dried oregano"))
            .addIngredient(new Ingredient(new BigDecimal(1), teaspoon, "dried cumin"))
            .addIngredient(new Ingredient(new BigDecimal(1), teaspoon, "sugar"))
            .addIngredient(new Ingredient(BigDecimal.valueOf(1.0 / 2.0), teaspoon, "salt"))
            .addIngredient(new Ingredient(new BigDecimal(1), tablespoon, "finely grated orange zest"))
            .addIngredient(new Ingredient(new BigDecimal(3), tablespoon, "fresh-squeezed orange juice"))
            .addIngredient(new Ingredient(new BigDecimal(2), tablespoon, "olive oil"))
            .addIngredient(new Ingredient(new BigDecimal(6), none, "skinless, boneless chicken thighs (1 1/4 pounds)"))
            .addIngredient(new Ingredient(new BigDecimal(8), small, "corn tortillas"))
            .addIngredient(new Ingredient(new BigDecimal(3), cups, "packed baby arugula (3 ounces)"))
            .addIngredient(new Ingredient(new BigDecimal(2), medium, "ripe avocados, sliced"))
            .addIngredient(new Ingredient(new BigDecimal(4), none, "radishes, thinly sliced"))
            .addIngredient(new Ingredient(BigDecimal.valueOf(1.0 / 2.0), pint, "cherry tomatoes, halved"))
            .addIngredient(new Ingredient(BigDecimal.valueOf(1.0 / 4.0), none, "red onion, thinly sliced"))
            .addIngredient(new Ingredient(null, none, "Roughly chopped cilantro"))
            .addIngredient(new Ingredient(BigDecimal.valueOf(1.0 / 2.0), cup, "sour cream thinned with 1/4 cup milk"))
            .addIngredient(new Ingredient(new BigDecimal(1), none, "lime, cut into wedges"));

    recipe.setNote(new Note("Look for anchor chile powder with the Mexican ingredients at your grocery store, " +
            "on buy it online. (If you can't find anchor chili powder, you replace the anchor chili, the oregano, " +
            "and the cumin with 2 1/2 tablespoons regular chili powder, though the flavor won't be quite the same.)"));
    recipes.add(recipe);

    log.info("Added recipe >> Spicy Grilled Chicken Tacos");

    return recipes;
  }
}