package com.example.c51diplompersonaltrainerrest.repository;

import com.example.c51diplompersonaltrainerrest.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
}
