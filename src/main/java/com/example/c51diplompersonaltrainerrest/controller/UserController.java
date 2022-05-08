package com.example.c51diplompersonaltrainerrest.controller;

import com.example.c51diplompersonaltrainerrest.mapper.UserMapper;
import com.example.c51diplompersonaltrainerrest.dto.UserDTO;
import com.example.c51diplompersonaltrainerrest.entity.Program;
import com.example.c51diplompersonaltrainerrest.entity.Role;
import com.example.c51diplompersonaltrainerrest.entity.User;
import com.example.c51diplompersonaltrainerrest.exception.InvalidParametrException;
import com.example.c51diplompersonaltrainerrest.exception.NotFoundException;
import com.example.c51diplompersonaltrainerrest.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(tags = "User", description = "Operations with the user")
@RequestMapping("/api/user")
public class UserController {

    private UserRepository userRepository;
    private UserMapper userMapper;

    public UserController(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
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
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "User change", notes = "This can only be done by the logged in user",
            authorizations = {@Authorization(value = "apiKey")})
    @PutMapping(value = "/{username}", produces = "application/json")
    public ResponseEntity<User> updateUser(@ApiParam(value = "The name that needs to be fetched", example = "test1")
                                           @PathVariable("username") String username,
                                           @ApiParam(value = "Modified user object", name = "body userDTO")
                                           @Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        if (username == null | userRepository.findByUsername(username).isEmpty()) {
            throw new NotFoundException();
        }
        if (bindingResult.hasErrors()){
            throw new InvalidParametrException();
        }
        User user = userRepository.findByUsername(username).get();
        List<Role> roleList = user.getRoleList();
        List<Program> programList = user.getProgramList();


        User updateUser = userMapper.userDTOToUser(userDTO);
        updateUser.setId(user.getId());
        updateUser.setRoleList(roleList);
        updateUser.setProgramList(programList);
        updateUser.setStatus(user.getStatus());

        return ResponseEntity.ok(userRepository.save(updateUser));
    }


}
