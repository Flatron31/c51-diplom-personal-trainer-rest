package com.example.c51diplompersonaltrainerrest.mapper;

import com.example.c51diplompersonaltrainerrest.dto.ExerciseDTO;
import com.example.c51diplompersonaltrainerrest.entity.Exercise;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ExerciseMapper {
    ExerciseDTO exerciseToExerciseDTO(Exercise exercise);
    Exercise exerciseDTOToExercise(ExerciseDTO exerciseDto);
}
