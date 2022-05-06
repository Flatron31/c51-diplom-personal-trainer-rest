package com.example.c51diplompersonaltrainerrest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopDTO {
    @Size(min = 3, max = 50)
    private String name;

    @Size(min = 3, max = 20)
    private String city;

    @Size(min = 3, max = 30)
    private String address;
}
