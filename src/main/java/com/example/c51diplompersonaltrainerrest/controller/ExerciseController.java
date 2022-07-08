package com.example.c51diplompersonaltrainerrest.controller;

import com.example.c51diplompersonaltrainerrest.dto.ExerciseDTO;
import com.example.c51diplompersonaltrainerrest.entity.Exercise;
import com.example.c51diplompersonaltrainerrest.entity.Status;
import com.example.c51diplompersonaltrainerrest.repository.ExerciseRepository;
import com.example.c51diplompersonaltrainerrest.service.ExerciseService;
import com.example.c51diplompersonaltrainerrest.validation.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "Exercise", description = "Operations with the exercise object")
@RequestMapping("/api/user/exercise")
public class ExerciseController {

    private final ExerciseRepository exerciseRepository;
    private final Validator validator;
    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseRepository exerciseRepository,
                              Validator validator,
                              ExerciseService exerciseService) {
        this.exerciseRepository = exerciseRepository;
        this.validator = validator;
        this.exerciseService = exerciseService;
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Creating a new exercise", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @PostMapping()
    public ResponseEntity<Exercise> createExercise(@ApiParam(value = "New object exercise", example = "exerciseDTO")
                               @Valid @RequestBody ExerciseDTO exerciseDTO,
                               BindingResult bindingResult) {
        validator.validate(bindingResult);

        return ResponseEntity.ok(exerciseService.createExercise(exerciseDTO));
    }


//    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @ApiOperation(value = "Getting exercise by id", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Exercise> getExercise(@ApiParam(value = "This id is required to get the exercise under the given id",
            example = "1")
                                                @PathVariable("id") Long id) {
        validator.validateExerciseId(id);

        return ResponseEntity.ok(exerciseRepository.getById(id));
    }


//    @PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "Updated exercise", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Exercise> updateExercise(@ApiParam(value = "The identifier is required to get the exercise " +
            "for this id for subsequent changes", example = "1")
                               @PathVariable("id") Long id,
                               @ApiParam(value = "Creating a modified exercise object",
                                       example = "exercise")
                               @Valid @RequestBody Exercise exercise, BindingResult bindingResult) {
        validator.validateExerciseId(id);
        validator.validate(bindingResult);

        exercise.setId(id);

        return ResponseEntity.ok(exerciseRepository.save(exercise));

    }

//    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @ApiOperation(value = "Getting all exercise objects", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Exercise>> getAllExercises() {
        List<Exercise> exerciseList = exerciseRepository.findAll();

        return ResponseEntity.ok(exerciseList);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @ApiOperation(value = "Removal exercise", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public void deleteExercise(@ApiParam(value = "Id is required to receive a comment on this id", example = "1")
                               @PathVariable("id") Long id,
                               @Valid @RequestBody Exercise exercise, BindingResult bindingResult) {
        validator.validateExerciseId(id);
        exercise = exerciseRepository.getById(id);
        exercise.setStatus(Status.DELETED);
        exerciseRepository.save(exercise);
    }
}

