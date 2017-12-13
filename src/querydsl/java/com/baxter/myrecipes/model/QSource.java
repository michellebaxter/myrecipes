package com.baxter.myrecipes.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSource is a Querydsl query type for Source
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSource extends EntityPathBase<Source> {

    private static final long serialVersionUID = -177904498L;

    public static final QSource source = new QSource("source");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QSource(String variable) {
        super(Source.class, forVariable(variable));
    }

    public QSource(Path<? extends Source> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSource(PathMetadata metadata) {
        super(Source.class, metadata);
    }

}

