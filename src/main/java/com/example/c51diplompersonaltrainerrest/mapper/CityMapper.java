package com.example.c51diplompersonaltrainerrest.mapper;

import com.example.c51diplompersonaltrainerrest.dto.CityDTO;
import com.example.c51diplompersonaltrainerrest.entity.City;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface CityMapper {
    CityDTO cityToCityDTO(City city);
    City cityDTOToCity(CityDTO cityDTO);
}
