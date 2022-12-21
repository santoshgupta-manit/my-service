package com.abn.assessment.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.abn.assessment.recipe.repository.entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Integer>, JpaSpecificationExecutor<Recipe> {

	Recipe findByNameIgnoreCase(String name);
}
