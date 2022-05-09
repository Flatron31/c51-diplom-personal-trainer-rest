package com.example.c51diplompersonaltrainerrest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SHOPS")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 3, max = 50)
    private String name;

    @Size(min = 3, max = 20)
    private String city;

    @Size(min = 3, max = 30)
    private String address;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "shopList")
    private List<SportsNutrition> sportsNutritionList;

}
