package com.example.c51diplompersonaltrainerrest.controller;

import com.example.c51diplompersonaltrainerrest.entity.Program;
import com.example.c51diplompersonaltrainerrest.entity.User;
import com.example.c51diplompersonaltrainerrest.exception.NotFoundException;
import com.example.c51diplompersonaltrainerrest.mapper.UserMapper;
import com.example.c51diplompersonaltrainerrest.repository.ProgramRepository;
import com.example.c51diplompersonaltrainerrest.repository.UserRepository;
import com.example.c51diplompersonaltrainerrest.service.ProgramService;
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

    private UserMapper userMapper;
    private UserRepository userRepository;
    private ProgramService programService;
    private ProgramRepository programRepository;

    public ProgramController(UserMapper userMapper,
                             UserRepository userRepository,
                             ProgramService programService,
                             ProgramRepository programRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.programService = programService;
        this.programRepository = programRepository;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @ApiOperation(value = "Creating a program for the user under the given id",
            authorizations = {@Authorization(value = "apiKey")})
    @PostMapping("/{id}")
    public ResponseEntity<Program> createProgramForUser(@ApiParam(value = "This id is required to search for " +
            "a user under this id", example = "1")
                                                        @PathVariable("id") long id) {
        if (id < 1 | userRepository.findById(id).isEmpty()) {
            throw new NotFoundException();
        }
        User user = userRepository.getById(id);
        List<Program> programList = user.getProgramList();
        Program program = programService.createProgram(user);
        programList.add(program);
        user.setProgramList(programList);

        Program save = programRepository.save(program);

        return ResponseEntity.ok(save);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @ApiOperation(value = "Getting all user programs by id", authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<List<Program>> getAllProgramUser(@ApiParam(value = "This id is required to search for " +
            "a user under this id", example = "1")
                                                           @PathVariable("id") long id) {
        if (id < 1 | userRepository.findById(id).isEmpty()) {
            throw new NotFoundException();
        }
        User user = userRepository.getById(id);
        List<Program> allProgramsUser = programRepository.findAllByUser(user);

        return ResponseEntity.ok(allProgramsUser);
    }
}
