package com.baxter.myrecipes;

import com.baxter.myrecipes.model.QRecipe;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.junit.Test;

import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class RecipeServiceTest {

    private RecipeService service = new RecipeService(null);

    @Test
    public void testAddCategoriesToBuilder() {
        BooleanBuilder builder = new BooleanBuilder();
        QRecipe recipe = QRecipe.recipe;
        List<Long> categoryIds = Arrays.asList(1L, 2L, 4L);
        builder = service.addCategoriesToBulder(builder, recipe, categoryIds);

        assertThat(builder.toString(), containsString("category.id = 1 &&"));
        assertThat(builder.toString(), containsString("category.id = 2 &&"));
        assertThat(builder.toString(), containsString("category.id = 4"));
    }

    @Test
    public void testBuildPredicateWithNameOnly() {
        RecipeFilter filter = RecipeFilter.builder().name("Potato").build();
        Predicate predicate = service.buildPredicate(filter);
        assertThat(predicate.toString(), equalTo("containsIc(recipe.name,Potato)"));
    }

    @Test
    public void testBuildPredicateWithAuthorOnly() {
        RecipeFilter filter = RecipeFilter.builder().author("Grace").build();
        Predicate predicate = service.buildPredicate(filter);
        assertThat(predicate.toString(), equalTo("containsIc(recipe.author,Grace)"));
    }

    @Test
    public void testBuildPredicateWithCommentsOnly() {
        RecipeFilter filter = RecipeFilter.builder().comments("E liked").build();
        Predicate predicate = service.buildPredicate(filter);
        assertThat(predicate.toString(), equalTo("containsIc(recipe.comments,E liked)"));
    }

    @Test
    public void testBuildPredicateWithSourceOnly() {
        RecipeFilter filter = RecipeFilter.builder().sourceId(6L).build();
        Predicate predicate = service.buildPredicate(filter);
        assertThat(predicate.toString(), equalTo("recipe.source.id = 6"));
    }

    @Test
    public void testBuildPredicateWithCourseOnly() {
        RecipeFilter filter = RecipeFilter.builder().courseId(7L).build();
        Predicate predicate = service.buildPredicate(filter);
        assertThat(predicate.toString(), equalTo("recipe.course.id = 7"));
    }

    @Test
    public void testBuildPredicateWithEthnicityOnly() {
        RecipeFilter filter = RecipeFilter.builder().ethnicityId(18L).build();
        Predicate predicate = service.buildPredicate(filter);
        assertThat(predicate.toString(), equalTo("recipe.ethnicity.id = 18"));
    }

    @Test
    public void testBuildPredicateWithMonthOnly() {
        RecipeFilter filter = RecipeFilter.builder().month(Month.DECEMBER).build();
        Predicate predicate = service.buildPredicate(filter);
        assertThat(predicate.toString(), equalTo("recipe.month = DECEMBER"));
    }

    @Test
    public void testBuildPredicateWithYearOnly() {
        RecipeFilter filter = RecipeFilter.builder().year(1995).build();
        Predicate predicate = service.buildPredicate(filter);
        assertThat(predicate.toString(), equalTo("recipe.year = 1995"));
    }

    @Test
    public void testBuildPredicateWithRatingOnly() {
        RecipeFilter filter = RecipeFilter.builder().ratedAtLeast(4).build();
        Predicate predicate = service.buildPredicate(filter);
        assertThat(predicate.toString(), equalTo("recipe.rating >= 4"));
    }

    @Test
    public void testBuildPredicateWithNoRatingOnly() {
        RecipeFilter filter = RecipeFilter.builder().ratedAtLeast(0).build();
        Predicate predicate = service.buildPredicate(filter);
        assertThat(predicate.toString(), equalTo("recipe.rating is null"));
    }

    @Test
    public void testBuildPredicateWithAllFilterFields() {
        RecipeFilter filter = RecipeFilter.builder()
                .name("Chicken")
                .author("Bobby Flay")
                .month(Month.JULY)
                .ethnicityId(13L)
                .sourceId(1L)
                .year(2011)
                .comments("grill")
                .courseId(6L)
                .categoryIds(Arrays.asList(3L, 4L))
                .ratedAtLeast(3)
                .build();
        Predicate builder = service.buildPredicate(filter);
        assertThat(builder.toString(), containsString("containsIc(recipe.name,Chicken)"));
        assertThat(builder.toString(), containsString("containsIc(recipe.author,Bobby Flay)"));
        assertThat(builder.toString(), containsString("recipe.month = JULY"));
        assertThat(builder.toString(), containsString("recipe.ethnicity.id = 13"));
        assertThat(builder.toString(), containsString("recipe.source.id = 1"));
        assertThat(builder.toString(), containsString("recipe.year = 2011"));
        assertThat(builder.toString(), containsString("containsIc(recipe.comments,grill)"));
        assertThat(builder.toString(), containsString("recipe.course.id = 6"));
        assertThat(builder.toString(), containsString("category.id = 3 &&"));
        assertThat(builder.toString(), containsString("category.id = 4"));
        assertThat(builder.toString(), containsString("recipe.rating >= 3"));
    }
}
