package com.example.c51diplompersonaltrainerrest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseDTO {

    @Size(min = 3, max = 50)
    private String name;

    @Size(min = 3)
    private String description;

    @Min(value = 1)
    private long numberOfApproaches;

    @Min(value = 1)
    private long numberOfTimes;
}
