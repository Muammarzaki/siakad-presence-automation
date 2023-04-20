package com.github.validators;

import com.github.validators.annotations.DayofWeek;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DayOfWeekValidation implements ConstraintValidator<DayofWeek, String> {

    public static final String DAY_OF_WEEK_PATTERN = "monday|tuesday|wednesday|thursday|friday|saturday|sunday";

    @Override
    public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
        return arg0.matches(DAY_OF_WEEK_PATTERN);
    }

}
