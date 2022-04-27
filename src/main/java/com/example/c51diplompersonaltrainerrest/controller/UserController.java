package com.example.c51diplompersonaltrainerrest.controller;

import com.example.c51diplompersonaltrainerrest.entity.User;
import com.example.c51diplompersonaltrainerrest.repository.UserRepository;
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

    @GetMapping(value = "/{username}")
    public ResponseEntity<User> get(@PathVariable("username") String username) {
        if (username == null | userRepository.findByUsername(username).isEmpty()) {
            ResponseEntity.badRequest();
            //throw new NotFoundException();
        }
        User getUser = userRepository.findByUsername(username).get();
        return ResponseEntity.ok(getUser);
    }


}
