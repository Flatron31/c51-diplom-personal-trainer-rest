package com.example.c51diplompersonaltrainerrest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SportsSupplement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 3, max = 50)
    private String name;

    @Size(min = 3)
    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "sportsSupplement_id")
    private List<Shop> shopList;
}
