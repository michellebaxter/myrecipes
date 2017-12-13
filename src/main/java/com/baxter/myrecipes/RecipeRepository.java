package com.baxter.myrecipes;

import com.baxter.myrecipes.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends QuerydslPredicateExecutor<Recipe>, JpaRepository<Recipe, Long> {

}
