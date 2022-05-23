package com.signature.recipe.model;

public enum Difficulty {

  EASY("Easy"),
  MODERATE("Moderate"),
  HARD("Hard");

  private final String name;

  Difficulty(String name) {
    this.name = name;
  }

  public String getDisplayName() {
    return this.name;
  }
}