package com.github.validators.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.validators.FreeDayOfMonthValidation;

import jakarta.validation.Constraint;

@Documented
@Constraint(validatedBy = FreeDayOfMonthValidation.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE_USE, ElementType.FIELD })
public @interface FreeDayOfMonth {

}
