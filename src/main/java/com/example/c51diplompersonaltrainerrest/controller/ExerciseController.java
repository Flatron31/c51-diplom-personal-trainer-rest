package com.example.c51diplompersonaltrainerrest.controller;

import com.example.c51diplompersonaltrainerrest.entity.Exercise;
import com.example.c51diplompersonaltrainerrest.exception.InvalidParametrException;
import com.example.c51diplompersonaltrainerrest.exception.NotFoundException;
import com.example.c51diplompersonaltrainerrest.repository.ExerciseRepository;
import com.example.c51diplompersonaltrainerrest.repository.UserRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/exercise")
public class ExerciseController {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private UserRepository userRepository;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @ApiOperation(value = "Getting exercise by id", authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Exercise> getExercise(@ApiParam(value = "This id is required to get the exercise under the given id",
            example = "1")
                                                @PathVariable("{id}") Long id) {
        if (id < 0 | exerciseRepository.findById(id).isEmpty()) {
            throw new NotFoundException();
        }
        Exercise exercise = exerciseRepository.findById(id).get();

        return ResponseEntity.ok(exercise);
    }

    @ApiResponse(responseCode = "200", description = "Successful operation")
    @ApiResponse(responseCode = "405", description = "Invalid input")
    @ApiOperation(value = "Creating a new exercise", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @PostMapping(value = "/{userId}", produces = "application/json")
    public ResponseEntity<Exercise> createExercize(@ApiParam(value = "UserId is required to get a user for this id",
            example = "1")
                                                   @PathVariable("userId") Long userId,
                                                   @Valid @RequestBody Exercise exercise, BindingResult bindingResult) {
        if (userId < 1 | userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException();
        }
        if (bindingResult.hasErrors()) {
            throw new InvalidParametrException();
        }
        Exercise saveExercize = exerciseRepository.save(exercise);

        return ResponseEntity.ok(saveExercize);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @ApiOperation(value = "Removal exercise", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @DeleteMapping(value = "/{userId}/{id}", produces = "application/json")
    public void deleteComment(@ApiParam(value = "UserId is required to get a user for this id", example = "1")
                              @PathVariable("userId") Long userId,
                              @ApiParam(value = "Id is required to receive a comment on this id", example = "1")
                              @PathVariable("id") Long id) {
        if (userId < 1 | userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException();
        }
        if (userId < 1 | exerciseRepository.findById(id).isEmpty()) {
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
    @PutMapping(value = "/{userId}/{id}", produces = "application/json")
    public ResponseEntity<Exercise> updateComment(@ApiParam(value = "UserId is required to get a user for this id", example = "1")
                                                  @PathVariable("userId") Long userId,
                                                  @ApiParam(value = "Id is required to receive a comment on this id", example = "1")
                                                  @PathVariable("id") Long id,
                                                  @ApiParam(value = "Creating a modified Exercise object", name = "body exercise")
                                                  @Valid @RequestBody Exercise exercise, BindingResult bindingResult) {
        if (userId < 1 | userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException();
        }
        if (userId < 1 | exerciseRepository.findById(id).isEmpty()) {
            throw new NotFoundException();
        }
        if (bindingResult.hasErrors()) {
            throw new InvalidParametrException();
        }
        exercise.setId(id);
        Exercise saveExercise = exerciseRepository.save(exercise);

        return ResponseEntity.ok(saveExercise);
    }
}

