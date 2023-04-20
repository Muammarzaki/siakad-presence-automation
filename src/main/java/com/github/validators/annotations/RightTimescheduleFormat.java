package com.github.validators.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.validators.ScheduleTimeValidators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
@Documented
@Target({ ElementType.TYPE_USE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ScheduleTimeValidators.class)
public @interface RightTimescheduleFormat {

    public String message() default "Schedule time format not valid";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
