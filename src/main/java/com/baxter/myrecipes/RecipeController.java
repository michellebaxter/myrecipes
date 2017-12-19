package com.baxter.myrecipes;

import com.baxter.myrecipes.model.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private static final int DEFAULT_PAGE_SIZE = 10000;
    private static final String DEFAULT_SORT = "name";

    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public List<Recipe> getRecipes(RecipeFilter filter,
                                   @PageableDefault(size = DEFAULT_PAGE_SIZE, sort = DEFAULT_SORT,
                                   direction = Sort.Direction.ASC)
                                   final Pageable pageable) {
        Page<Recipe> recipes = recipeService.findAll(filter, pageable);
        return recipes.getContent();
    }

    @GetMapping("{id}")
    public Recipe getRecipe(@PathVariable Long id) {
        return recipeService.findById(id);
    }

    //todo: post, put, delete
}
