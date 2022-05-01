package com.example.c51diplompersonaltrainerrest.controller;

import com.example.c51diplompersonaltrainerrest.entity.User;
import com.example.c51diplompersonaltrainerrest.exception.NotFoundException;
import com.example.c51diplompersonaltrainerrest.repository.UserRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @ApiOperation(value = "Get user by user name", authorizations = {@Authorization(value = "apiKey")})
    @GetMapping(value = "/{username}", produces = "application/json")
    public ResponseEntity<User> get(@ApiParam(value = "The name that needs to be fetched. Use user1 for testing", example = "username")
                                    @PathVariable("username") String username) {

        if (username == null | userRepository.findByUsername(username).isEmpty()) {
            throw new NotFoundException();
        }
        User getUser = userRepository.findByUsername(username).get();

        return ResponseEntity.ok(getUser);
    }



}
