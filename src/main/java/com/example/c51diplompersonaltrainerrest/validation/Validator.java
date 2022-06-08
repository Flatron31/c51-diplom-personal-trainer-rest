package com.example.c51diplompersonaltrainerrest.validation;

import com.example.c51diplompersonaltrainerrest.exception.InvalidParametrException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class Validator {

    public void validate(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidParametrException();
        }
    }
}
