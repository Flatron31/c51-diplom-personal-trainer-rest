package com.example.c51diplompersonaltrainerrest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @ManyToMany ///( надо сделать ДТО для использования в контроллере)
    private List<Exercise> exerciseList;

    @ManyToMany ///( надо сделать ДТО для использования в контроллере)
    private List<SportsSupplement> sportsSupplements;



}
