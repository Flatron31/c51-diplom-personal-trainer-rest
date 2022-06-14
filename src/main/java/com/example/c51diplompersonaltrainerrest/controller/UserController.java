package com.example.c51diplompersonaltrainerrest.controller;

import com.example.c51diplompersonaltrainerrest.entity.Status;
import com.example.c51diplompersonaltrainerrest.dto.UserDTO;
import com.example.c51diplompersonaltrainerrest.entity.User;
import com.example.c51diplompersonaltrainerrest.repository.UserRepository;
import com.example.c51diplompersonaltrainerrest.service.UserService;
import com.example.c51diplompersonaltrainerrest.validation.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
@RestController
@Api(tags = "User", description = "Operations with the user")
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;
    private final Validator validator;
    private final UserService userService;

    public UserController(UserRepository userRepository,
                          Validator validator,
                          UserService userService) {
        this.userRepository = userRepository;
        this.validator = validator;
        this.userService = userService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @ApiOperation(value = "Get user by username", authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(value = "/{username}", produces = "application/json")
    public ResponseEntity<User> getUser(@ApiParam(value = "The name that needs to be fetched", example = "username")
                                        @PathVariable("username") String username) {
        userService.validateUserName(username);

        return ResponseEntity.ok(userRepository.findByUsername(username).get());
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
        validator.validate(bindingResult);
        userService.validateUserName(username);

        User updateUser = userService.updateUser(username, userDTO);

        return ResponseEntity.ok(updateUser);
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
        userService.validateUserId(id);

        User user = userRepository.getById(id);
        user.setStatus(Status.DELETED);
        userRepository.save(user);
    }
}
