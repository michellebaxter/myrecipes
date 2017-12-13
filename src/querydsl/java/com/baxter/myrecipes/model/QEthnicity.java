package com.baxter.myrecipes.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QEthnicity is a Querydsl query type for Ethnicity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QEthnicity extends EntityPathBase<Ethnicity> {

    private static final long serialVersionUID = 933842732L;

    public static final QEthnicity ethnicity = new QEthnicity("ethnicity");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QEthnicity(String variable) {
        super(Ethnicity.class, forVariable(variable));
    }

    public QEthnicity(Path<? extends Ethnicity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEthnicity(PathMetadata metadata) {
        super(Ethnicity.class, metadata);
    }

}

