package com.baxter.myrecipes.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ethnicities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ethnicity {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

}
