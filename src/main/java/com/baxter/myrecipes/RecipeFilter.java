package com.baxter.myrecipes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Month;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeFilter {
    private String name;
    private String author;
    private Long sourceId;
    private Month month;
    private Integer year;
    private Long courseId;
    private Long ethnicityId;
    private List<Long> categoryIds;
    private String comments;
    private Integer ratedAtLeast;
}
