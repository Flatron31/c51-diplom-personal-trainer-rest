package com.example.c51diplompersonaltrainerrest.controller;

import com.example.c51diplompersonaltrainerrest.entity.Program;
import com.example.c51diplompersonaltrainerrest.entity.User;
import com.example.c51diplompersonaltrainerrest.repository.ProgramRepository;
import com.example.c51diplompersonaltrainerrest.repository.UserRepository;
import com.example.c51diplompersonaltrainerrest.service.ProgramService;
import com.example.c51diplompersonaltrainerrest.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "Program", description = "Operations with the program object")
@RequestMapping("/api/user/program")
public class ProgramController {

    private final UserRepository userRepository;
    private final ProgramService programService;
    private final ProgramRepository programRepository;
    private final UserService userService;

    public ProgramController(UserRepository userRepository,
                             ProgramService programService,
                             ProgramRepository programRepository,
                             UserService userService) {
        this.userRepository = userRepository;
        this.programService = programService;
        this.programRepository = programRepository;
        this.userService = userService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @ApiOperation(value = "Creating a program for the user under the given id",
            authorizations = {@Authorization(value = "apiKey")})
    @PostMapping("/{id}")
    public void createProgramForUser(@ApiParam(value = "This id is required to search for " +
            "a user under this id", example = "1")
                                                        @PathVariable("id") long id) {
        userService.validateUserId(id);

        programService.createProgram(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @ApiOperation(value = "Getting all user programs by id", authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<List<Program>> getAllProgramUser(@ApiParam(value = "This ID is required to search for" +
            " a user under this ID", example = "1")
                                                           @PathVariable("id") long id) {
        userService.validateUserId(id);

        User user = userRepository.getById(id);

        return ResponseEntity.ok(programRepository.findAllByUser(user));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @ApiOperation(value = "Deleting a program object", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @DeleteMapping(value = "/{id}")
    public void deleteProgram(@ApiParam(value = "This id is required to search for " +
            "a user under this id", example = "1")
                              @PathVariable("id") long id) {
        userService.validateUserId(id);

        Program program = programRepository.getById(id);
        programRepository.delete(program);
    }
}
