package com.baxter.myrecipes;

import com.baxter.myrecipes.model.Recipe;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Month;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeRepositoryTest {

    private static final String RECIPE_NAME = "Test Recipe";
    private static final String AUTHOR = "Michelle";
    private static final String COMMENTS = "A Save Test";
    private static final int YEAR = 2017;
    private static final String LOCATION = "location, location, location!";
    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    public void testSave() {
        Recipe recipe = Recipe.builder()
                .name(RECIPE_NAME)
                .author(AUTHOR)
                .comments(COMMENTS)
                .month(Month.DECEMBER)
                .year(YEAR)
                .location(LOCATION)
                .build();
        Recipe savedRecipe = recipeRepository.save(recipe);
        assertThat(savedRecipe).isNotNull();
        assertThat(savedRecipe.getId()).isNotNull();

        Optional<Recipe> foundRecipe = recipeRepository.findById(savedRecipe.getId());
        if (foundRecipe.isPresent()) {
            verifyRecipe(foundRecipe.get());
        } else {
            fail("Saved recipe " + savedRecipe.getId() + " could not be found.");
        }
    }

    private void verifyRecipe(Recipe recipe) {
        assertThat(recipe.getName()).isEqualTo(RECIPE_NAME);
        assertThat(recipe.getAuthor()).isEqualTo(AUTHOR);
        assertThat(recipe.getComments()).isEqualTo(COMMENTS);
        assertThat(recipe.getMonth()).isEqualTo(Month.DECEMBER);
        assertThat(recipe.getYear()).isEqualTo(YEAR);
        assertThat(recipe.getLocation()).isEqualTo(LOCATION);
    }
}
