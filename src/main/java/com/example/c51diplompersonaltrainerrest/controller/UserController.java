package com.example.c51diplompersonaltrainerrest.controller;

import com.example.c51diplompersonaltrainerrest.entity.Status;
import com.example.c51diplompersonaltrainerrest.mapper.UserMapper;
import com.example.c51diplompersonaltrainerrest.dto.UserDTO;
import com.example.c51diplompersonaltrainerrest.entity.Program;
import com.example.c51diplompersonaltrainerrest.entity.Role;
import com.example.c51diplompersonaltrainerrest.entity.User;
import com.example.c51diplompersonaltrainerrest.exception.InvalidParametrException;
import com.example.c51diplompersonaltrainerrest.exception.NotFoundException;
import com.example.c51diplompersonaltrainerrest.repository.UserRepository;
import com.example.c51diplompersonaltrainerrest.validation.Validator;
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
@Api(tags = "User", description = "Operations with the user")
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final Validator validator;

    public UserController(UserRepository userRepository,
                          UserMapper userMapper,
                          Validator validator) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.validator = validator;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @ApiOperation(value = "Get user by username", authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(value = "/{username}", produces = "application/json")
    public ResponseEntity<User> get(@ApiParam(value = "The name that needs to be fetched", example = "username")
                                    @PathVariable("username") String username) {
        if (username == null | userRepository.findByUsername(username).isEmpty()) {
            throw new InvalidParametrException();
        }
        User getUser = userRepository.findByUsername(username).get();

        return ResponseEntity.ok(getUser);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "User change", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @PutMapping(value = "/{username}", produces = "application/json")
    public ResponseEntity<User> updateUser(@ApiParam(value = "The name that needs to be fetched", example = "test1")
                                           @PathVariable("username") String username,
                                           @ApiParam(value = "Modified user object", example = "userDTO")
                                           @Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        if (username == null | userRepository.findByUsername(username).isEmpty()) {
            throw new NotFoundException();
        }
        validator.validate(bindingResult);

        User user = userRepository.findByUsername(username).get();
        List<Role> roleList = user.getRoleList();
        List<Program> programList = user.getProgramList();

        User updateUser = userMapper.userDTOToUser(userDTO);
        updateUser.setId(user.getId());
        updateUser.setUsername(username);
        updateUser.setRoleList(roleList);
        updateUser.setProgramList(programList);
        updateUser.setStatus(user.getStatus());

        log.info("User {} changed successfully", username);

        return ResponseEntity.ok(userRepository.save(updateUser));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @ApiOperation(value = "Deleting a user", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @DeleteMapping(value = "/{id}")
    public void deleteUser(@ApiParam(value = "This ID is required to search for a user under this ID", example = "1")
                              @PathVariable("id") long id) {
        if (id < 1 | userRepository.findById(id).isEmpty()) {
            throw new NotFoundException();
        }
        User user = userRepository.getById(id);
        user.setStatus(Status.DELETED);
        userRepository.save(user);
    }




}
