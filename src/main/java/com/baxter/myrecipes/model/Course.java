package com.baxter.myrecipes.model;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "courses")
@Getter
public class Course {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

}
