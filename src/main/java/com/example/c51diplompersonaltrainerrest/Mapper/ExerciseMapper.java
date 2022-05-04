package com.example.c51diplompersonaltrainerrest.Mapper;

import com.example.c51diplompersonaltrainerrest.dto.ExerciseDTO;
import com.example.c51diplompersonaltrainerrest.entity.Exercise;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {
    ExerciseDTO exerciseDTOToExercise(Exercise exercise);
    Exercise exerciseDTOToExercise(ExerciseDTO exerciseDto);
}
