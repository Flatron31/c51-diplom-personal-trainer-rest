package com.example.c51diplompersonaltrainerrest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    ///( надо сделать ДТО для использования в контроллере)
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "programList")
    private List<Exercise> exerciseList;


    ///( надо сделать ДТО для использования в контроллере)
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SportsNutrition> sportsNutritionList;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
