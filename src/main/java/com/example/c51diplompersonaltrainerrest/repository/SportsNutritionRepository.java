package com.example.c51diplompersonaltrainerrest.repository;

import com.example.c51diplompersonaltrainerrest.entity.SportsNutrition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface SportsNutritionRepository extends JpaRepository<SportsNutrition, Long> {
}
