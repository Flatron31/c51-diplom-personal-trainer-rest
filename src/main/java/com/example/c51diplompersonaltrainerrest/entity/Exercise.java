package com.example.c51diplompersonaltrainerrest.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EXERCISES")
@Builder
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 3, max = 50)
    private String username;

    @Size(min = 3)
    private String description;

    @Min(value = 1)
    private long numberOfApproaches;

    @Min(value = 1)
    private long numberOfTimes;

    @ManyToMany
    private List<Program> programList;
}
