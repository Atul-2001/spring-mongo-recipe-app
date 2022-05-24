package com.signature.recipe.repository;

import com.signature.recipe.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
@ExtendWith(SpringExtension.class)
class UnitOfMeasureRepositoryTest {

  @Autowired
  public UnitOfMeasureRepository unitOfMeasureRepository;

  @BeforeEach
  void setUp() {
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

  @Test
  @DirtiesContext
  void findByDescription() {
    Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByDescription("Tablespoon");

    assertNotNull(unitOfMeasure.orElse(null));

    assertEquals("Tablespoon", unitOfMeasure.get().getDescription());
  }

  @Test
  @DirtiesContext
  void findByDescription2() {
    Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByDescription("Medium");

    assertNotNull(unitOfMeasure.orElse(null));

    assertEquals("Medium", unitOfMeasure.get().getDescription());
  }
}