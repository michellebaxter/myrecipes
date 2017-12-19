package com.baxter.myrecipes;

import com.baxter.myrecipes.model.QRecipe;
import com.baxter.myrecipes.model.Recipe;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class RecipeService {

    private RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Page<Recipe> findAll(RecipeFilter filter, Pageable pageable) {
        return recipeRepository.findAll(buildPredicate(filter), pageable);
    }

    Predicate buildPredicate(RecipeFilter filter) {
        BooleanBuilder builder = new BooleanBuilder();
        QRecipe recipe = QRecipe.recipe;
        builder = StringUtils.isEmpty(filter.getName()) ? builder :
                builder.and(recipe.name.containsIgnoreCase(filter.getName()));
        builder = StringUtils.isEmpty(filter.getAuthor()) ? builder :
                builder.and(recipe.author.containsIgnoreCase(filter.getAuthor()));
        builder = filter.getSourceId() == null ? builder :
                builder.and(recipe.source.id.eq(filter.getSourceId()));
        builder = filter.getCourseId() == null ? builder :
                builder.and(recipe.course.id.eq(filter.getCourseId()));
        builder = filter.getEthnicityId() == null ? builder :
                builder.and(recipe.ethnicity.id.eq(filter.getEthnicityId()));
        builder = filter.getMonth() == null ? builder :
                builder.and(recipe.month.eq(filter.getMonth()));
        builder = filter.getYear() == null ? builder :
                builder.and(recipe.year.eq(filter.getYear()));
        builder = StringUtils.isEmpty(filter.getComments()) ? builder :
                builder.and(recipe.comments.containsIgnoreCase(filter.getComments()));
        builder = CollectionUtils.isEmpty(filter.getCategoryIds()) ? builder :
                addCategoriesToBulder(builder, recipe, filter.getCategoryIds());
        return builder;
    }

    BooleanBuilder addCategoriesToBulder(BooleanBuilder builder, QRecipe recipe, List<Long> categoryIds) {
        for (Long categoryId : categoryIds) {
            builder = builder.and(recipe.categories.any().category.id.eq(categoryId));
        }
        return builder;
    }

    public Recipe findById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(()-> new DataRetrievalFailureException("Could not find recipe with id " + id));
    }

    public Recipe save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public void delete(Long recipeId) {
        recipeRepository.deleteById(recipeId);
    }
}
