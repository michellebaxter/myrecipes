package com.baxter.myrecipes;

import com.baxter.myrecipes.model.RecipeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeCategoryRepository extends JpaRepository<RecipeCategory, Long> {

}
