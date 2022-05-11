package com.example.c51diplompersonaltrainerrest.service;

import com.example.c51diplompersonaltrainerrest.entity.*;
import com.example.c51diplompersonaltrainerrest.repository.ExerciseRepository;
import com.example.c51diplompersonaltrainerrest.repository.SportsNutritionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.c51diplompersonaltrainerrest.entity.SportsSupplement.*;

@Service
public class ProgramService {

    private SportsNutritionRepository sportsNutritionRepository;
    private ExerciseRepository exerciseRepository;

    public ProgramService(SportsNutritionRepository sportsNutritionRepository, ExerciseRepository exerciseRepository) {
        this.sportsNutritionRepository = sportsNutritionRepository;
        this.exerciseRepository = exerciseRepository;
    }

    public Program createProgram(User user) {
        List <Exercise> exerciseList = new ArrayList<>();
        List <SportsNutrition> sportsNutritionList = new ArrayList<>();
        Program program = new Program();

        if (user.getMission().equals(Mission.FORCE)) {
            if (user.getAge() < 18L) {
                sportsNutritionList = sportsNutritionRepository.findAllBySportsSupplement(PROTEIN);
            } else {
                sportsNutritionList = sportsNutritionRepository.findAllBySportsSupplement(SPECIAL_DRUGS);
            }
            exerciseList = exerciseRepository.findAll();

            program.setExerciseList(exerciseList);
            program.setSportsNutritionList(sportsNutritionList);
            program.setUser(user);

            return program;
        }

        else if (user.getMission().equals(Mission.LOSE_WEIGHT)){
            if (user.getAge() < 18L) {
                sportsNutritionList = sportsNutritionRepository.findAllBySportsSupplement(PROTEIN);
            } else {
                sportsNutritionList = sportsNutritionRepository.findAllBySportsSupplement(FAT_BURNERS);
            }
            exerciseList = exerciseRepository.findAll();

            program.setExerciseList(exerciseList);
            program.setSportsNutritionList(sportsNutritionList);
            program.setUser(user);

            return program;
        }

        else if (user.getMission().equals(Mission.RELIEF)){
            if (user.getAge() < 18L) {
                sportsNutritionList = sportsNutritionRepository.findAllBySportsSupplement(PROTEIN);
            } else {
                sportsNutritionList = sportsNutritionRepository.findAllBySportsSupplement(AMINO_ACIDS);
            }
            exerciseList = exerciseRepository.findAll();

            program.setExerciseList(exerciseList);
            program.setSportsNutritionList(sportsNutritionList);
            program.setUser(user);

            return program;
        }

        else if (user.getMission().equals(Mission.WEIGHT_GAIN)){
            if (user.getAge() < 18L) {
                sportsNutritionList = sportsNutritionRepository.findAllBySportsSupplement(PROTEIN);
            } else {
                sportsNutritionList = sportsNutritionRepository.findAllBySportsSupplementAndSportsSupplementAndSportsSupplement(SPECIAL_DRUGS,
                        CREATINE, GEINER);
            }
            exerciseList = exerciseRepository.findAll();

            program.setExerciseList(exerciseList);
            program.setSportsNutritionList(sportsNutritionList);
            program.setUser(user);

            return program;
        }
        else {
            sportsNutritionList = sportsNutritionRepository.findAll();
            exerciseList = exerciseRepository.findAll();
            program.setExerciseList(exerciseList);
            program.setSportsNutritionList(sportsNutritionList);
            program.setUser(user);
            return program;
        }
    }

}
