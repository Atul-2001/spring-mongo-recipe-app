package com.signature.recipe.service;

import com.signature.recipe.data.UnitOfMeasureDTO;
import com.signature.recipe.model.UnitOfMeasure;
import com.signature.recipe.repository.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class UnitOfMeasureService {

  private final UnitOfMeasureRepository unitOfMeasureRepository;

  public UnitOfMeasureService(UnitOfMeasureRepository unitOfMeasureRepository) {
    this.unitOfMeasureRepository = unitOfMeasureRepository;
  }

  public List<UnitOfMeasureDTO> getAll() {
    return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(),
            false).map(UnitOfMeasure::getDTO).collect(Collectors.toList());
  }
}