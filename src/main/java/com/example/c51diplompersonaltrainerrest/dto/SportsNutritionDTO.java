package com.example.c51diplompersonaltrainerrest.dto;

import com.example.c51diplompersonaltrainerrest.entity.SportsSupplement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SportsNutritionDTO {

    @Size(min = 3, max = 50)
    private String name;

    @Size(min = 3)
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    private SportsSupplement sportsSupplement;

}
