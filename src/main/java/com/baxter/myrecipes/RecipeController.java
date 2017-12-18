package com.baxter.myrecipes;

import com.baxter.myrecipes.model.Recipe;
import com.querydsl.core.BooleanBuilder;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private RecipeRepository recipeRepository;

    public RecipeController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @GetMapping
    public List<Recipe> getRecipes() { // todo (mibaxter): add filter later
        Page<Recipe> recipes = recipeRepository.findAll(new BooleanBuilder(), PageRequest.of(0, 10000));
        return recipes.getContent();
    }

    @GetMapping("{id}")
    public Recipe getRecipe(@PathVariable Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(()-> new DataRetrievalFailureException("Could not find recipe with id " + id));
    }
}
