package com.github.validators.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.validators.DayOfWeekValidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * DayofWeek
 */
@Documented
@Constraint(validatedBy = DayOfWeekValidation.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE_USE, ElementType.FIELD })
public @interface DayofWeek {
    public String message() default "Day is invalid try to check your calender";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}