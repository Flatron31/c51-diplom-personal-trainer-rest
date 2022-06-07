package com.example.c51diplompersonaltrainerrest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityDTO {

    @Size(min = 1, max = 50)
    private String name;
}
