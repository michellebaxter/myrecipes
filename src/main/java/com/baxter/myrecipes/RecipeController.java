package com.baxter.myrecipes;

import com.baxter.myrecipes.model.Category;
import com.baxter.myrecipes.model.Recipe;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private RecipeRepository recipeRepository;
    private CategoryRepository categoryRepository;

    public RecipeController(RecipeRepository recipeRepository, CategoryRepository categoryRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<Recipe> getRecipes() { // add filter later
        Page<Recipe> recipes = recipeRepository.findAll(new BooleanBuilder(), PageRequest.of(0, 10000));
        return recipes.getContent();
    }

    @GetMapping("categories")
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }
}
