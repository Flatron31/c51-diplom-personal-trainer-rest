package com.example.c51diplompersonaltrainerrest.controller;

import com.example.c51diplompersonaltrainerrest.entity.Exercise;
import com.example.c51diplompersonaltrainerrest.repository.ExerciseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/exercise")
public class ExerciseController {
    private ExerciseRepository exerciseRepository;

    public ExerciseController(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getExercise(@PathVariable("{id}") Long id){
        if (id < 0 || exerciseRepository.findById(id).isEmpty()){
            ResponseEntity.badRequest();
        }
        Exercise exercise = exerciseRepository.findById(id).get();
        return ResponseEntity.ok(exercise);
    }

}

