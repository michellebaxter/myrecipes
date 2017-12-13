package com.baxter.myrecipes.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecipe is a Querydsl query type for Recipe
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRecipe extends EntityPathBase<Recipe> {

    private static final long serialVersionUID = -216313343L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecipe recipe = new QRecipe("recipe");

    public final StringPath author = createString("author");

    public final ListPath<RecipeCategory, QRecipeCategory> categories = this.<RecipeCategory, QRecipeCategory>createList("categories", RecipeCategory.class, QRecipeCategory.class, PathInits.DIRECT2);

    public final StringPath comments = createString("comments");

    public final QCourse course;

    public final QEthnicity ethnicity;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath location = createString("location");

    public final EnumPath<java.time.Month> month = createEnum("month", java.time.Month.class);

    public final StringPath name = createString("name");

    public final QSource source;

    public final NumberPath<Integer> year = createNumber("year", Integer.class);

    public QRecipe(String variable) {
        this(Recipe.class, forVariable(variable), INITS);
    }

    public QRecipe(Path<? extends Recipe> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecipe(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecipe(PathMetadata metadata, PathInits inits) {
        this(Recipe.class, metadata, inits);
    }

    public QRecipe(Class<? extends Recipe> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.course = inits.isInitialized("course") ? new QCourse(forProperty("course")) : null;
        this.ethnicity = inits.isInitialized("ethnicity") ? new QEthnicity(forProperty("ethnicity")) : null;
        this.source = inits.isInitialized("source") ? new QSource(forProperty("source")) : null;
    }

}

