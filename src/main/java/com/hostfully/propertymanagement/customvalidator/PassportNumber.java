package com.hostfully.propertymanagement.customvalidator;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Pattern(regexp = "^[a-zA-Z0-9]{6,20}$", message = "Invalid Passport Number.")
@ReportAsSingleViolation
public @interface PassportNumber {
    String message() default "Invalid Passport Number.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}