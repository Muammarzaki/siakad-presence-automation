package com.github.validators;

import com.github.validators.annotations.FreeDayOfMonth;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FreeDayOfMonthValidation implements ConstraintValidator<FreeDayOfMonth, String> {
    public static final String FREE_DAY_OF_MONTH_PATTERN = "^([1-9]|[12][0-9]|3[01]):(1[0-2]|\\d)$";

    @Override
    public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
        return arg0.matches(FREE_DAY_OF_MONTH_PATTERN);
    }

}
