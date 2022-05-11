package com.example.c51diplompersonaltrainerrest.controller;

import com.example.c51diplompersonaltrainerrest.dto.ExerciseDTO;
import com.example.c51diplompersonaltrainerrest.mapper.ExerciseMapper;
import com.example.c51diplompersonaltrainerrest.entity.Exercise;
import com.example.c51diplompersonaltrainerrest.exception.InvalidParametrException;
import com.example.c51diplompersonaltrainerrest.exception.NotFoundException;
import com.example.c51diplompersonaltrainerrest.repository.ExerciseRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "Exercise", description = "Operations with the exercise object")
@RequestMapping("/api/user/exercise")
public class ExerciseController {

    private ExerciseRepository exerciseRepository;
    private ExerciseMapper exerciseMapper;

    public ExerciseController(ExerciseRepository exerciseRepository, ExerciseMapper exerciseMapper) {
        this.exerciseRepository = exerciseRepository;
        this.exerciseMapper = exerciseMapper;
    }

    @ApiResponse(responseCode = "200", description = "Successful operation")
    @ApiResponse(responseCode = "405", description = "Invalid input")
    @ApiOperation(value = "Creating a new exercise", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @PostMapping()
    public ResponseEntity<Exercise> createExercize(@ApiParam(value = "New object exercise", example = "exerciseDTO")
                                                   @Valid @RequestBody ExerciseDTO exerciseDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("New exercise not added");
            throw new InvalidParametrException();
        }
        Exercise exercise = exerciseMapper.exerciseDTOToExercise(exerciseDTO);

        log.info("New exercise {} added", exerciseDTO.getName());

        return ResponseEntity.ok(exerciseRepository.save(exercise));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @ApiOperation(value = "Getting exercise by id")
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Exercise> getExercise(@ApiParam(value = "This id is required to get the exercise under the given id",
            example = "1")
                                                @PathVariable("id") Long id) {
        if (id < 0 | exerciseRepository.findById(id).isEmpty()) {
            throw new NotFoundException();
        }
        Exercise exercise = exerciseRepository.getById(id);

        return ResponseEntity.ok(exercise);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @ApiOperation(value = "Removal exercise", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public void deleteComment(@ApiParam(value = "Id is required to receive a comment on this id", example = "1")
                              @PathVariable("id") Long id) {
        if (id < 1 | exerciseRepository.findById(id).isEmpty()) {
            throw new NotFoundException();
        }
        exerciseRepository.delete(exerciseRepository.getById(id));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Exercise not found"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Updated exercise", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Exercise> updateComment(@ApiParam(value = "The identifier is required to get the exercise " +
            "for this id for subsequent changes", example = "1")
                                                  @PathVariable("id") Long id,
                                                  @ApiParam(value = "Creating a modified Exercise object",
                                                          example = "exercise")
                                                  @Valid @RequestBody Exercise exercise, BindingResult bindingResult) {
        if (id < 1 | exerciseRepository.findById(id).isEmpty()) {
            throw new NotFoundException();
        }
        if (bindingResult.hasErrors()) {
            throw new InvalidParametrException();
        }
        exercise.setId(id);

        return ResponseEntity.ok(exerciseRepository.save(exercise));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
    })
    @ApiOperation(value = "Getting all exercise objects", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Exercise>> getAllExercises() {
        List<Exercise> exerciseList = exerciseRepository.findAll();

        return ResponseEntity.ok(exerciseList);
    }
}

