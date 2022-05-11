package com.example.c51diplompersonaltrainerrest.controller;

import com.example.c51diplompersonaltrainerrest.configuration.security.Jwt.JwtTokenProvider;
import com.example.c51diplompersonaltrainerrest.dto.AuthRequestDTO;
import com.example.c51diplompersonaltrainerrest.dto.UserDTO;
import com.example.c51diplompersonaltrainerrest.entity.User;
import com.example.c51diplompersonaltrainerrest.exception.InvalidParametrException;
import com.example.c51diplompersonaltrainerrest.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthentificationController {
    private final UserService service;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthentificationController(UserService service, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.service = service;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "User registration")
    @PostMapping("/reg")
    public ResponseEntity<UserDTO> registration(@ApiParam(value = "Create a new user object", example = "userDTO")
                                                @Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidParametrException();
        }
        if (service.existByUsername(userDTO.getUsername()) || service.existByEmail(userDTO.getEmail())) {
            log.info("{} not registered", userDTO.getUsername());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        service.registration(userDTO);

        log.info("Registered successfully {}", userDTO.getUsername());

        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @ApiOperation(value = "User authorization")
    @PostMapping("/login")
    public ResponseEntity<Map<Object, Object>> logIn(@ApiParam(value = "Authorization object", example = "requestDto")
                                                     @Valid @RequestBody AuthRequestDTO requestDto,
                                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidParametrException();
        }

        String username = requestDto.getUsername();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
        User user = service.findByUsername(username);

        String token = jwtTokenProvider.generateToken(username, user.getRoleList());

        Map<Object, Object> resp = new HashMap<>();
        resp.put("username", username);
        resp.put("token", token);

        log.info("Authorized {}", username);

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }


    @GetMapping("/logout")
    public ResponseEntity<Map<Object, Object>> logOut(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<Object, Object> resp = new HashMap<>();

        if (auth != null) {
            resp.put("username", auth.getName());
            resp.put("session, lastAccessedTime", session.getLastAccessedTime());
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        log.info("Logout");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
