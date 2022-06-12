package com.example.c51diplompersonaltrainerrest.service;

import com.example.c51diplompersonaltrainerrest.dto.ExerciseDTO;
import com.example.c51diplompersonaltrainerrest.entity.Exercise;
import com.example.c51diplompersonaltrainerrest.mapper.ExerciseMapper;
import com.example.c51diplompersonaltrainerrest.repository.ExerciseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseMapper exerciseMapper;

    public ExerciseService(ExerciseRepository exerciseRepository,
                           ExerciseMapper exerciseMapper) {
        this.exerciseRepository = exerciseRepository;
        this.exerciseMapper = exerciseMapper;
    }

    public Exercise createExercise(ExerciseDTO exerciseDTO){
        Exercise exercise = exerciseMapper.exerciseDTOToExercise(exerciseDTO);
        log.info("New exercise {} added", exerciseDTO.getName());
        exerciseRepository.save(exercise);
        return exercise;
    }

}
