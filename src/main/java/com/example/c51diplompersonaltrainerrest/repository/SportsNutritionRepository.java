package com.example.c51diplompersonaltrainerrest.repository;

import com.example.c51diplompersonaltrainerrest.entity.SportsNutrition;
import com.example.c51diplompersonaltrainerrest.entity.SportsSupplement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SportsNutritionRepository extends JpaRepository<SportsNutrition, Long> {
    List<SportsNutrition> findAllBySportsSupplement (SportsSupplement sportsSupplement);
    List<SportsNutrition> findAllBySportsSupplementAndSportsSupplementAndSportsSupplement (SportsSupplement sportsSupplement1,
                                                                                           SportsSupplement sportsSupplement2,
                                                                                           SportsSupplement sportsSupplement3);
}
