package com.example.c51diplompersonaltrainerrest.service;

import com.example.c51diplompersonaltrainerrest.entity.*;
import com.example.c51diplompersonaltrainerrest.repository.ExerciseRepository;
import com.example.c51diplompersonaltrainerrest.repository.ProgramRepository;
import com.example.c51diplompersonaltrainerrest.repository.SportsNutritionRepository;
import com.example.c51diplompersonaltrainerrest.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.c51diplompersonaltrainerrest.entity.SportsSupplement.*;

@Slf4j
@Service
public class ProgramService {

    private final SportsNutritionRepository sportsNutritionRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;
    private final ProgramRepository programRepository;

    public ProgramService(SportsNutritionRepository sportsNutritionRepository,
                          ExerciseRepository exerciseRepository,
                          UserRepository userRepository,
                          ProgramRepository programRepository) {
        this.sportsNutritionRepository = sportsNutritionRepository;
        this.exerciseRepository = exerciseRepository;
        this.userRepository = userRepository;
        this.programRepository = programRepository;
    }

    public void createProgram(long id) {
        User user = userRepository.getById(id);
        List<Program> programList = user.getProgramList();

        List<Exercise> exerciseList = new ArrayList<>();
        List<SportsNutrition> sportsNutritionList = new ArrayList<>();

        Program program = new Program();

        if (user.getMission().equals(Mission.FORCE)) {
            if (user.getAge() < 18) {
                sportsNutritionList = sportsNutritionRepository.findAllBySportsSupplement(PROTEIN);
            } else {
                sportsNutritionList = sportsNutritionRepository.findAllBySportsSupplement(SPECIAL_DRUGS);
            }
            exerciseList = exerciseRepository.findAll();

            program.setExerciseList(exerciseList);
            program.setSportsNutritionList(sportsNutritionList);
            program.setUser(user);

        } else if (user.getMission().equals(Mission.LOSE_WEIGHT)) {
            if (user.getAge() < 18) {
                sportsNutritionList = sportsNutritionRepository.findAllBySportsSupplement(PROTEIN);
            } else {
                sportsNutritionList = sportsNutritionRepository.findAllBySportsSupplement(FAT_BURNERS);
            }
            exerciseList = exerciseRepository.findAll();

            program.setExerciseList(exerciseList);
            program.setSportsNutritionList(sportsNutritionList);
            program.setUser(user);

        } else if (user.getMission().equals(Mission.RELIEF)) {
            if (user.getAge() < 18) {
                sportsNutritionList = sportsNutritionRepository.findAllBySportsSupplement(PROTEIN);
            } else {
                sportsNutritionList = sportsNutritionRepository.findAllBySportsSupplement(PROTEIN);
            }
            exerciseList = exerciseRepository.findAll();

            program.setExerciseList(exerciseList);
            program.setSportsNutritionList(sportsNutritionList);
            program.setUser(user);

        } else if (user.getMission().equals(Mission.WEIGHT_GAIN)) {
            if (user.getAge() < 18) {
                sportsNutritionList = sportsNutritionRepository.findAllBySportsSupplement(PROTEIN);
            } else {
                sportsNutritionList = sportsNutritionRepository.findAllBySportsSupplementAndSportsSupplementAndSportsSupplement(SPECIAL_DRUGS,
                        CREATINE, GEINER);
            }
            exerciseList = exerciseRepository.findAll();

            program.setExerciseList(exerciseList);
            program.setSportsNutritionList(sportsNutritionList);
            program.setUser(user);

        } else {
            sportsNutritionList = sportsNutritionRepository.findAll();
            exerciseList = exerciseRepository.findAll();
            program.setExerciseList(exerciseList);
            program.setSportsNutritionList(sportsNutritionList);
            program.setUser(user);
        }

        programList.add(program);
        user.setProgramList(programList);
        programRepository.save(program);
        log.info("New program added {}", user.getUsername());
    }
}
