package com.baxter.myrecipes;

import com.baxter.myrecipes.model.Category;
import com.baxter.myrecipes.model.Course;
import com.baxter.myrecipes.model.Ethnicity;
import com.baxter.myrecipes.model.Recipe;
import com.baxter.myrecipes.model.RecipeCategory;
import com.baxter.myrecipes.model.Source;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceIntegrationTest {

    @Autowired
    private RecipeService service;

    @Test
    public void testSave() {
        Recipe recipe = Recipe.builder()
                .name("Hamantschen")
                .month(Month.OCTOBER)
                .year(1997)
                .source(Source.builder().id(3L).build())
                .course(Course.builder().id(10L).build())
                .comments("Jewish pastry for Purim; a pain!")
                .build();
        recipe.setCategories(buildCategories(recipe, 2L, 3L));

        recipe = service.save(recipe);

        assertThat(recipe).isNotNull();
        assertThat(recipe.getId()).isGreaterThan(0L);
        List<RecipeCategory> recipeCategories = recipe.getCategories();
        assertThat(recipeCategories).hasSize(2);
        assertThat(recipeCategories.get(0).getId()).isGreaterThan(0L);
        assertThat(recipeCategories.get(0).getRecipe().getId()).isEqualTo(recipe.getId());
        assertThat(recipeCategories.get(1).getId()).isGreaterThan(0L);
        assertThat(recipeCategories.get(1).getRecipe().getId()).isEqualTo(recipe.getId());

        service.delete(recipe.getId());
    }

    private List<RecipeCategory> buildCategories(Recipe recipe, Long... categoryIds) {
        List<RecipeCategory> categories = new ArrayList<>();
        for (Long categoryId : categoryIds) {
            categories.add(RecipeCategory.builder()
                    .category(Category.builder().id(categoryId).build())
                    .recipe(recipe)
                    .build())
            ;
        }
        return categories;
    }

    @Test
    public void testFindByCategories() {
        Recipe recipe = Recipe.builder()
                .name("Recipe with categories Healthy & Make Ahead")
                .build();
        recipe.setCategories(buildCategories(recipe, 2L, 3L));
        recipe = service.save(recipe);

        Recipe fastRecipe = Recipe.builder()
                .name("Fast recipe")
                .build();
        fastRecipe.setCategories(buildCategories(fastRecipe, 1L));
        service.save(fastRecipe);

        Recipe fastAndHealthyAndMakeAhead = Recipe.builder()
                .name("Recipe with categories Fast, Healthy, & Make Ahead")
                .build();
        fastAndHealthyAndMakeAhead.setCategories(buildCategories(fastAndHealthyAndMakeAhead, 1L, 2L, 3L));
        fastAndHealthyAndMakeAhead = service.save(fastAndHealthyAndMakeAhead);

        RecipeFilter filter = RecipeFilter.builder()
                .categoryIds(Arrays.asList(2L, 3L))
                .build();
        Page<Recipe> recipePage = service.findAll(filter, PageRequest.of(0, 10, Sort.by("name")));

        assertThat(recipePage.getTotalElements()).isEqualTo(2);
        assertThat(recipePage.getContent().get(0).getId()).isEqualTo(fastAndHealthyAndMakeAhead.getId());
        assertThat(recipePage.getContent().get(1).getId()).isEqualTo(recipe.getId());

        service.delete(recipe.getId());
        service.delete(fastRecipe.getId());
        service.delete(fastAndHealthyAndMakeAhead.getId());
    }

    @Test
    public void testFindByName() {
        Recipe recipe = Recipe.builder()
                .name("Potato Galette")
                .build();
        recipe = service.save(recipe);

        Recipe potpie = Recipe.builder()
                .name("Chicken Potpie")
                .build();
        potpie = service.save(potpie);

        Recipe meatloaf = Recipe.builder()
                .name("Mom's meatloaf with mashed potatoes")
                .build();
        meatloaf = service.save(meatloaf);

        RecipeFilter filter = RecipeFilter.builder()
                .name("POTATO")
                .build();
        Page<Recipe> recipePage = service.findAll(filter, PageRequest.of(0, 10, Sort.by("name")));

        assertThat(recipePage.getTotalElements()).isEqualTo(2);
        assertThat(recipePage.getContent().get(0).getId()).isEqualTo(meatloaf.getId());
        assertThat(recipePage.getContent().get(1).getId()).isEqualTo(recipe.getId());

        service.delete(recipe.getId());
        service.delete(potpie.getId());
        service.delete(meatloaf.getId());
    }

    @Test
    public void testFindByAuthor() {
        Recipe recipe = Recipe.builder()
                .author("Grace Parisi")
                .build();
        recipe = service.save(recipe);

        Recipe bobby = Recipe.builder()
                .author("Bobby Flay")
                .build();
        bobby = service.save(bobby);

        Recipe celeb = Recipe.builder()
                .author("Paris Hilton")
                .build();
        celeb = service.save(celeb);

        RecipeFilter filter = RecipeFilter.builder()
                .author("paris")
                .build();
        Page<Recipe> recipePage = service.findAll(filter, PageRequest.of(0, 10, Sort.by("author")));

        assertThat(recipePage.getTotalElements()).isEqualTo(2);
        assertThat(recipePage.getContent().get(0).getId()).isEqualTo(recipe.getId());
        assertThat(recipePage.getContent().get(1).getId()).isEqualTo(celeb.getId());

        service.delete(recipe.getId());
        service.delete(bobby.getId());
        service.delete(celeb.getId());
    }

    @Test
    public void testFindByComments() {
        Recipe recipe = Recipe.builder()
                .comments("A pain to make")
                .build();
        recipe = service.save(recipe);

        Recipe recipe2 = Recipe.builder()
                .comments("Makes a lot!")
                .build();
        recipe2 = service.save(recipe2);

        Recipe recipe3 = Recipe.builder()
                .comments("made too much")
                .build();
        recipe3 = service.save(recipe3);

        RecipeFilter filter = RecipeFilter.builder()
                .comments(" to")
                .build();
        Page<Recipe> recipePage = service.findAll(filter, PageRequest.of(0, 10, Sort.by("comments")));

        assertThat(recipePage.getTotalElements()).isEqualTo(2);
        assertThat(recipePage.getContent().get(0).getId()).isEqualTo(recipe.getId());
        assertThat(recipePage.getContent().get(1).getId()).isEqualTo(recipe3.getId());

        service.delete(recipe.getId());
        service.delete(recipe2.getId());
        service.delete(recipe3.getId());
    }

    @Test
    public void testFindBySource() {
        Recipe recipe = Recipe.builder()
                .name("A recipe")
                .source(Source.builder().id(1L).build())
                .build();
        recipe = service.save(recipe);

        Recipe recipe2 = Recipe.builder()
                .name("Recipe Two")
                .source(Source.builder().id(2L).build())
                .build();
        recipe2 = service.save(recipe2);

        Recipe recipe3 = Recipe.builder()
                .name("No source")
                .build();
        recipe3 = service.save(recipe3);

        RecipeFilter filter = RecipeFilter.builder()
                .sourceId(2L)
                .build();
        Page<Recipe> recipePage = service.findAll(filter, PageRequest.of(0, 10));

        assertThat(recipePage.getTotalElements()).isEqualTo(1);
        assertThat(recipePage.getContent().get(0).getId()).isEqualTo(recipe2.getId());

        service.delete(recipe.getId());
        service.delete(recipe2.getId());
        service.delete(recipe3.getId());
    }

    @Test
    public void testFindByCourse() {
        Recipe recipe = Recipe.builder()
                .name("A recipe")
                .course(Course.builder().id(2L).build())
                .build();
        recipe = service.save(recipe);

        Recipe recipe2 = Recipe.builder()
                .name("Recipe Two")
                .course(Course.builder().id(2L).build())
                .build();
        recipe2 = service.save(recipe2);

        Recipe recipe3 = Recipe.builder()
                .name("No course")
                .build();
        recipe3 = service.save(recipe3);

        RecipeFilter filter = RecipeFilter.builder()
                .courseId(2L)
                .build();
        Page<Recipe> recipePage = service.findAll(filter, PageRequest.of(0, 10, Sort.by("name")));

        assertThat(recipePage.getTotalElements()).isEqualTo(2);
        assertThat(recipePage.getContent().get(0).getId()).isEqualTo(recipe.getId());
        assertThat(recipePage.getContent().get(1).getId()).isEqualTo(recipe2.getId());

        service.delete(recipe.getId());
        service.delete(recipe2.getId());
        service.delete(recipe3.getId());
    }

    @Test
    public void testFindByEthnicity() {
        Recipe recipe = Recipe.builder()
                .name("A recipe")
                .ethnicity(Ethnicity.builder().id(1L).build())
                .build();
        recipe = service.save(recipe);

        Recipe recipe2 = Recipe.builder()
                .name("Recipe Two")
                .ethnicity(Ethnicity.builder().id(10L).build())
                .build();
        recipe2 = service.save(recipe2);

        Recipe recipe3 = Recipe.builder()
                .name("No ethnicity")
                .build();
        recipe3 = service.save(recipe3);

        RecipeFilter filter = RecipeFilter.builder()
                .ethnicityId(1L)
                .build();
        Page<Recipe> recipePage = service.findAll(filter, PageRequest.of(0, 10));

        assertThat(recipePage.getTotalElements()).isEqualTo(1);
        assertThat(recipePage.getContent().get(0).getId()).isEqualTo(recipe.getId());

        service.delete(recipe.getId());
        service.delete(recipe2.getId());
        service.delete(recipe3.getId());
    }

    @Test
    public void testFindByMonth() {
        Recipe recipe = Recipe.builder()
                .name("A recipe")
                .month(Month.JANUARY)
                .build();
        recipe = service.save(recipe);

        Recipe recipe2 = Recipe.builder()
                .name("Recipe Two")
                .month(Month.JULY)
                .build();
        recipe2 = service.save(recipe2);

        Recipe recipe3 = Recipe.builder()
                .name("No month")
                .build();
        recipe3 = service.save(recipe3);

        RecipeFilter filter = RecipeFilter.builder()
                .month(Month.JULY)
                .build();
        Page<Recipe> recipePage = service.findAll(filter, PageRequest.of(0, 10));

        assertThat(recipePage.getTotalElements()).isEqualTo(1);
        assertThat(recipePage.getContent().get(0).getId()).isEqualTo(recipe2.getId());

        service.delete(recipe.getId());
        service.delete(recipe2.getId());
        service.delete(recipe3.getId());
    }

    @Test
    public void testFindByYear() {
        Recipe recipe = Recipe.builder()
                .name("A recipe")
                .month(Month.JANUARY)
                .year(1989)
                .build();
        recipe = service.save(recipe);

        Recipe recipe2 = Recipe.builder()
                .name("Recipe Two")
                .month(Month.JULY)
                .year(1989)
                .build();
        recipe2 = service.save(recipe2);

        Recipe recipe3 = Recipe.builder()
                .name("Recipe 3")
                .month(Month.JULY)
                .year(1988)
                .build();
        recipe3 = service.save(recipe3);

        RecipeFilter filter = RecipeFilter.builder()
                .year(1989)
                .build();
        Page<Recipe> recipePage = service.findAll(filter, PageRequest.of(0, 10, Sort.by("month")));

        assertThat(recipePage.getTotalElements()).isEqualTo(2);
        assertThat(recipePage.getContent().get(0).getId()).isEqualTo(recipe.getId());
        assertThat(recipePage.getContent().get(1).getId()).isEqualTo(recipe2.getId());

        service.delete(recipe.getId());
        service.delete(recipe2.getId());
        service.delete(recipe3.getId());
    }

    @Test
    public void testFindByRatingSelected() {
        Recipe recipe = Recipe.builder()
                .name("A recipe")
                .month(Month.JANUARY)
                .year(1989)
                .rating(5)
                .build();
        recipe = service.save(recipe);

        Recipe recipe2 = Recipe.builder()
                .name("Recipe Two")
                .month(Month.JULY)
                .year(1989)
                .rating(4)
                .build();
        recipe2 = service.save(recipe2);

        Recipe recipe3 = Recipe.builder()
                .name("Recipe 3")
                .month(Month.JULY)
                .year(1988)
                .build();
        recipe3 = service.save(recipe3);

        RecipeFilter filter = RecipeFilter.builder()
                .ratedAtLeast(4)
                .build();
        Page<Recipe> recipePage = service.findAll(filter, PageRequest.of(0, 10, Sort.by("name")));

        assertThat(recipePage.getTotalElements()).isEqualTo(2);
        assertThat(recipePage.getContent().get(0).getId()).isEqualTo(recipe.getId());
        assertThat(recipePage.getContent().get(1).getId()).isEqualTo(recipe2.getId());

        service.delete(recipe.getId());
        service.delete(recipe2.getId());
        service.delete(recipe3.getId());
    }

    @Test
    public void testFindByNoRating() {
        Recipe recipe = Recipe.builder()
                .name("A recipe")
                .month(Month.JANUARY)
                .year(1989)
                .rating(5)
                .build();
        recipe = service.save(recipe);

        Recipe recipe2 = Recipe.builder()
                .name("Recipe Two")
                .month(Month.JULY)
                .year(1989)
                .rating(4)
                .build();
        recipe2 = service.save(recipe2);

        Recipe recipe3 = Recipe.builder()
                .name("Recipe 3")
                .month(Month.JULY)
                .year(1988)
                .build();
        recipe3 = service.save(recipe3);

        RecipeFilter filter = RecipeFilter.builder()
                .ratedAtLeast(0)
                .build();
        Page<Recipe> recipePage = service.findAll(filter, PageRequest.of(0, 10));

        assertThat(recipePage.getTotalElements()).isEqualTo(1);
        assertThat(recipePage.getContent().get(0).getId()).isEqualTo(recipe3.getId());

        service.delete(recipe.getId());
        service.delete(recipe2.getId());
        service.delete(recipe3.getId());
    }

    @Test
    public void testFindByRatingNotSet() {
        Recipe recipe = Recipe.builder()
                .name("A recipe")
                .month(Month.JANUARY)
                .year(1989)
                .rating(5)
                .build();
        recipe = service.save(recipe);

        Recipe recipe2 = Recipe.builder()
                .name("Recipe Two")
                .month(Month.JULY)
                .year(1989)
                .rating(4)
                .build();
        recipe2 = service.save(recipe2);

        Recipe recipe3 = Recipe.builder()
                .name("Recipe 3")
                .month(Month.JULY)
                .year(1988)
                .build();
        recipe3 = service.save(recipe3);

        RecipeFilter filter = RecipeFilter.builder()
                .build();
        Page<Recipe> recipePage = service.findAll(filter, PageRequest.of(0, 10));

        assertThat(recipePage.getTotalElements()).isEqualTo(3);

        service.delete(recipe.getId());
        service.delete(recipe2.getId());
        service.delete(recipe3.getId());
    }

    @Test
    public void testFindWithMultipleFilters() {
        Recipe recipe = Recipe.builder()
                .name("A recipe")
                .source(Source.builder().id(1L).build())
                .course(Course.builder().id(2L).build())
                .month(Month.JANUARY)
                .year(1989)
                .build();
        recipe.setCategories(buildCategories(recipe, 1L, 2L));
        recipe = service.save(recipe);

        Recipe recipe2 = Recipe.builder()
                .course(Course.builder().id(2L).build())
                .name("Recipe Two")
                .month(Month.JULY)
                .year(1989)
                .build();
        recipe2.setCategories(buildCategories(recipe2, 1L, 2L, 3L));
        recipe2 = service.save(recipe2);

        Recipe recipe3 = Recipe.builder()
                .name("Recipe 3")
                .source(Source.builder().id(1L).build())
                .course(Course.builder().id(2L).build())
                .month(Month.JULY)
                .year(1988)
                .build();
        recipe3 = service.save(recipe3);

        Recipe recipe4 = Recipe.builder()
                .name("Recipe 4")
                .source(Source.builder().id(1L).build())
                .course(Course.builder().id(2L).build())
                .month(Month.APRIL)
                .year(1989)
                .build();
        recipe4.setCategories(buildCategories(recipe4, 2L, 3L));
        recipe4 = service.save(recipe4);

        RecipeFilter filter = RecipeFilter.builder()
                .sourceId(1L)
                .courseId(2L)
                .year(1989)
                .categoryIds(Collections.singletonList(2L))
                .ratedAtLeast(0)
                .build();
        Page<Recipe> recipePage = service.findAll(filter, PageRequest.of(0, 10, Sort.by("name")));

        assertThat(recipePage.getTotalElements()).isEqualTo(2);
        assertThat(recipePage.getContent().get(0).getId()).isEqualTo(recipe.getId());
        assertThat(recipePage.getContent().get(1).getId()).isEqualTo(recipe4.getId());

        service.delete(recipe.getId());
        service.delete(recipe2.getId());
        service.delete(recipe3.getId());
        service.delete(recipe4.getId());
    }

    @Test
    public void testDelete() {
        Recipe recipe = Recipe.builder()
                .name("A recipe")
                .build();
        recipe = service.save(recipe);

        assertThat(recipe.getId()).isNotNull();
        assertThat(recipe.getId()).isGreaterThan(0L);

        service.delete(recipe.getId());

        try {
            service.findById(recipe.getId());
            fail("Exception should have been thrown.");
        } catch (DataRetrievalFailureException ignored) {

        }
    }

    @Test
    public void testUpdate() {
        Recipe recipe = Recipe.builder()
                .name("Bechamel Sauce")
                .month(Month.MARCH)
                .year(2003)
                .source(Source.builder().id(3L).build())
                .build();
        recipe = service.save(recipe);

        recipe = service.findById(recipe.getId());
        assertThat(recipe.getComments()).isNullOrEmpty();
        assertThat(recipe.getRating()).isNull();

        recipe.setComments("Overly complicated for not much taste");
        recipe.setRating(3);

        service.save(recipe);
        recipe = service.findById(recipe.getId());

        assertThat(recipe.getRating()).isEqualTo(3);
        assertThat(recipe.getComments()).isEqualTo("Overly complicated for not much taste");

        service.delete(recipe.getId());
    }
}
