package com.baxter.myrecipes;

import com.baxter.myrecipes.model.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @PostMapping
    public ResponseEntity<String> createRecipe(@RequestBody Recipe recipe) {
        Recipe savedRecipe = recipeService.save(recipe);
        return ResponseEntity.created(getLocationURI(savedRecipe.getId())).body(null);
    }

    private URI getLocationURI(long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateRecipe(@PathVariable Long id, @RequestBody Recipe recipe) {
        recipe.setId(id);
        recipeService.save(recipe);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().build().toUri()).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        recipeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
