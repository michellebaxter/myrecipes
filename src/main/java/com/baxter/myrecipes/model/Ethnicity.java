package com.baxter.myrecipes.model;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ethnicities")
@Getter
public class Ethnicity {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

}
