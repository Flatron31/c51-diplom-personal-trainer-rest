package com.example.c51diplompersonaltrainerrest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SPORTNUTRITIONS")
public class SportsNutrition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 3, max = 50)
    private String name;

    @Size(min = 3)
    private String description;

    @ManyToMany (fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Shop> shopList;

    @JsonIgnore
    @ManyToMany
    private List<Program> listProgram;

    @NonNull
    @Enumerated(EnumType.STRING)
    private SportsSupplement sportsSupplement;
}
