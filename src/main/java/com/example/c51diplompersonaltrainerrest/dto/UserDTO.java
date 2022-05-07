package com.example.c51diplompersonaltrainerrest.dto;

import com.example.c51diplompersonaltrainerrest.entity.Mission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @Size(min = 3, max = 50)
    private String username;

    private String password;

    @Size(min = 3, max = 50)
    private String firstName;

    @Size(min = 3, max = 50)
    private String lastName;

    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @Min(value = 1)
    private long age;

    @Min(value = 1)
    private long weight;

    @Min(value = 1)
    private long growth;

    @Enumerated(EnumType.STRING)
    private Mission mission;
}
