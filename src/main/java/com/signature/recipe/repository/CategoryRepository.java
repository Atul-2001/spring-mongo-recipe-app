package com.signature.recipe.repository;

import com.signature.recipe.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category, String> {

  Optional<Category> findByDescription(String description);
}