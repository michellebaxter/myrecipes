package com.baxter.myrecipes.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.Month;
import java.time.Year;
import java.util.List;

@Entity
@Table(name = "recipes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @ManyToOne
    @JoinColumn(name = "source_id", referencedColumnName = "id")
    private Source source;

    @Column(name = "month")
    @Enumerated(EnumType.STRING)
    private Month month;

    @Column(name = "year")
    private int year;

    @Column(name = "location")
    private String location;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "ethnicity_id", referencedColumnName = "id")
    private Ethnicity ethnicity;

    @OneToMany(mappedBy = "recipeId")
    private List<RecipeCategory> categories;

    @Column(name = "comments")
    private String comments;

}
