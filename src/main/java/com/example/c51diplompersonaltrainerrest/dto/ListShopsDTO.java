package com.example.c51diplompersonaltrainerrest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListShopsDTO {

    private List<Long> idShops;
}
