package com.example.c51diplompersonaltrainerrest.converter;

import com.example.c51diplompersonaltrainerrest.dto.UserDTO;
import com.example.c51diplompersonaltrainerrest.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    private static BCryptPasswordEncoder passwordEncoder;

    public UserConverter(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public static User convertToUserFromUserSignupDTO(UserDTO userDTO) {
        return User.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .build();
    }
}
