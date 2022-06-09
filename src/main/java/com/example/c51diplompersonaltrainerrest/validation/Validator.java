package com.example.c51diplompersonaltrainerrest.validation;

import com.example.c51diplompersonaltrainerrest.exception.InvalidParametrException;
import com.example.c51diplompersonaltrainerrest.exception.NotFoundException;
import com.example.c51diplompersonaltrainerrest.repository.ExerciseRepository;
import com.example.c51diplompersonaltrainerrest.repository.ShopRepository;
import com.example.c51diplompersonaltrainerrest.repository.SportsNutritionRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class Validator {

    private final ExerciseRepository exerciseRepository;
    private final ShopRepository shopRepository;
    private final SportsNutritionRepository sportsNutritionRepository;

    public Validator(ExerciseRepository exerciseRepository,
                     ShopRepository shopRepository,
                     SportsNutritionRepository sportsNutritionRepository) {
        this.exerciseRepository = exerciseRepository;
        this.shopRepository = shopRepository;
        this.sportsNutritionRepository = sportsNutritionRepository;
    }

    public void validate(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidParametrException();
        }
    }

    public void validateExerciseId(long id) {
        if (id < 0 | exerciseRepository.findById(id).isEmpty()) {
            throw new NotFoundException();
        }
    }

    public void validateShopId(long id){
        if (id < 0 | shopRepository.findById(id).isEmpty()) {
            throw new NotFoundException();
        }
    }

    public void validateSportsNutritionId(long id){
        if (id < 1 | sportsNutritionRepository.findById(id).isEmpty()) {
            throw new NotFoundException();
        }
    }
}
