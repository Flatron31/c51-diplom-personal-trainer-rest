package com.example.c51diplompersonaltrainerrest.Mapper;

import com.example.c51diplompersonaltrainerrest.dto.SportsNutritionDTO;
import com.example.c51diplompersonaltrainerrest.entity.SportsNutrition;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SportsNutritionMapper {
    SportsNutritionDTO SportsNutritionDTOToSportsNutrition(SportsNutrition sportsNutrition);
    SportsNutrition SportsNutritionDTOToSportsNutrition(SportsNutritionDTO sportsNutritionDTO);
}
