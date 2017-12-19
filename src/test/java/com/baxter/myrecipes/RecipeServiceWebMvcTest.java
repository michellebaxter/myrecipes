package com.baxter.myrecipes;

import com.baxter.myrecipes.model.Recipe;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@WebMvcTest(RecipeService.class)
@RunWith(SpringRunner.class)
@EnableSpringDataWebSupport
public class RecipeServiceWebMvcTest {

    @MockBean
    private RecipeRepository repository;

    @Autowired
    private RecipeService recipeService;

    @Test
    public void testFindById() {
        long recipeId = 33L;

        given(repository.findById(recipeId)).willReturn(Optional.of(new Recipe()));

        Recipe recipe = recipeService.findById(recipeId);

        assertThat(recipe).isNotNull();
    }

    @Test(expected = DataRetrievalFailureException.class)
    public void testFindWhenIdNotFound() {
        given(repository.findById(any(Long.class))).willThrow(new DataRetrievalFailureException("test"));

        recipeService.findById(1L);
    }

}
