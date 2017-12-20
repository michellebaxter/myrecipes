package com.baxter.myrecipes;

import com.baxter.myrecipes.model.Recipe;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Month;
import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecipeController.class)
@RunWith(SpringRunner.class)
@EnableSpringDataWebSupport
public class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeService recipeService;

    @Test
    public void testGetRecipes() throws Exception {
        final String RECIPE_NAME = "Test Recipe";
        final String AUTHOR = "Michelle";
        final String COMMENTS = "A Save Test";
        final int YEAR = 2017;
        final String LOCATION = "location, location, location!";

        Recipe recipe = Recipe.builder()
                .name(RECIPE_NAME)
                .author(AUTHOR)
                .comments(COMMENTS)
                .month(Month.DECEMBER)
                .year(YEAR)
                .location(LOCATION)
                .build();
        PageImpl<Recipe> page = new PageImpl<>(Collections.singletonList(recipe));
        given(recipeService.findAll(any(RecipeFilter.class), any(Pageable.class))).willReturn(page);

        mockMvc.perform(get("/recipes"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].name", is(RECIPE_NAME)))
                .andExpect(jsonPath("$.[0].author", is(AUTHOR)))
                .andExpect(jsonPath("$.[0].comments", is(COMMENTS)))
                .andExpect(jsonPath("$.[0].month", is(Month.DECEMBER.name())))
                .andExpect(jsonPath("$.[0].year", is(YEAR)))
                .andExpect(jsonPath("$.[0].location", is(LOCATION)))
                .andExpect(jsonPath("$.[0].source", nullValue()))
                .andExpect(jsonPath("$.[0].ethnicity", nullValue()))
                .andExpect(jsonPath("$.[0].categories", nullValue()))
                .andExpect(jsonPath("$.[0].course", nullValue()))
        ;
    }

    @Test
    public void testGetRecipe() throws Exception {
        final long id = 1L;
        Recipe recipe = Recipe.builder()
                .id(id)
                .build();
        given(recipeService.findById(id)).willReturn(recipe);
        mockMvc.perform(get("/recipes/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int)id)));
    }

    @Test
    public void testCreateRecipe() throws Exception {
        final String recipeName = "Test Saving a Recipe";
        Recipe savedRecipe = Recipe.builder()
                .id(2L)
                .name(recipeName)
                .build();
        given(recipeService.save(any(Recipe.class))).willReturn(savedRecipe);
        mockMvc.perform(post("/recipes")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{ \"name\" : \"" + recipeName + "\" }"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", equalTo("http://localhost/recipes/2")));
    }

    @Test
    public void testUpdateRecipe() throws Exception {
        final String recipeName = "Test Recipe";
        final int rating = 4;
        final long recipeId = 3L;
        Recipe updatedRecipe = Recipe.builder()
                .id(recipeId)
                .name(recipeName)
                .rating(rating)
                .build();
        given(recipeService.save(any(Recipe.class))).willReturn(updatedRecipe);
        String body = "{ \"name\" : \"" + recipeName + "\", \"rating\" : " + rating + " }";
        mockMvc.perform(put("/recipes/" + recipeId)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(body))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", equalTo("http://localhost/recipes/" + recipeId)));
    }

    @Test
    public void testDeleteRecipe() throws Exception {
        final Long recipeId = 13L;
        mockMvc.perform(delete("/recipes/" + recipeId))
                .andDo(print())
                .andExpect(status().isOk());
        verify(recipeService, times(1)).delete(recipeId);
    }

}
