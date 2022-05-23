package com.signature.recipe.repository;

import com.signature.recipe.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class UnitOfMeasureRepositoryTest {

  @Autowired
  UnitOfMeasureRepository unitOfMeasureRepository;

  @BeforeEach
  void setUp() {
  }

  @Test
//  @DirtiesContext
  void findByDescription() {
    Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByDescription("Tablespoon");

    assertNotNull(unitOfMeasure.orElse(null));

    assertEquals("Tablespoon", unitOfMeasure.get().getDescription());
  }

  @Test
//  @DirtiesContext
  void findByDescription2() {
    Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByDescription("Medium");

    assertNotNull(unitOfMeasure.orElse(null));

    assertEquals("Medium", unitOfMeasure.get().getDescription());
  }
}