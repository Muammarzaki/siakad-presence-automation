package com.github.validators.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.validators.FreeDayOfMonthValidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = FreeDayOfMonthValidation.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE_USE, ElementType.FIELD })
public @interface DateFormatter {
    public String message() default "DayofMonth is invalid try to follow year-date-month exp: 2023-31-12";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
