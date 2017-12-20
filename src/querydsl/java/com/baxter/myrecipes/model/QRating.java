package com.baxter.myrecipes.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QRating is a Querydsl query type for Rating
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRating extends EntityPathBase<Rating> {

    private static final long serialVersionUID = -219501040L;

    public static final QRating rating = new QRating("rating");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> value = createNumber("value", Integer.class);

    public QRating(String variable) {
        super(Rating.class, forVariable(variable));
    }

    public QRating(Path<? extends Rating> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRating(PathMetadata metadata) {
        super(Rating.class, metadata);
    }

}

