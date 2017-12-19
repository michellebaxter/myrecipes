package com.baxter.myrecipes.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecipeCategory is a Querydsl query type for RecipeCategory
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRecipeCategory extends EntityPathBase<RecipeCategory> {

    private static final long serialVersionUID = 785048095L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecipeCategory recipeCategory = new QRecipeCategory("recipeCategory");

    public final QCategory category;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QRecipe recipe;

    public QRecipeCategory(String variable) {
        this(RecipeCategory.class, forVariable(variable), INITS);
    }

    public QRecipeCategory(Path<? extends RecipeCategory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecipeCategory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecipeCategory(PathMetadata metadata, PathInits inits) {
        this(RecipeCategory.class, metadata, inits);
    }

    public QRecipeCategory(Class<? extends RecipeCategory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category")) : null;
        this.recipe = inits.isInitialized("recipe") ? new QRecipe(forProperty("recipe"), inits.get("recipe")) : null;
    }

}

