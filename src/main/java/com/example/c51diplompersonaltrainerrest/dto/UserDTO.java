package com.example.c51diplompersonaltrainerrest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;

    private long age;
    private long weight;
    private long growth;
}
