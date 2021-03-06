package com.signature.recipe.repository;

import com.signature.recipe.model.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, String> {

  Optional<UnitOfMeasure> findByDescription(String description);
}