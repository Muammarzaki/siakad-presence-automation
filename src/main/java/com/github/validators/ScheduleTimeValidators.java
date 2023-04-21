package com.github.validators;

import com.github.validators.annotations.RightTimescheduleFormat;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * ScheduleTimeValidators
 */
public class ScheduleTimeValidators implements ConstraintValidator<RightTimescheduleFormat, String> {
    public static final String SCHEDULE_TIME_FORMAT = "^(\\d{2}:\\d{2})-(\\d{2}:\\d{2})$";

    @Override
    public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
        return arg0.matches(SCHEDULE_TIME_FORMAT);
    }

}