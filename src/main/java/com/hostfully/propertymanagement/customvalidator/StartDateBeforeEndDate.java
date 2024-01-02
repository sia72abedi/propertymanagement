package com.hostfully.propertymanagement.customvalidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = startDateBeforeEndDateValidator.class)
@Target( { ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface StartDateBeforeEndDate {
    String message() default "StartDate Should Be Before Or Equal To EndDate.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}