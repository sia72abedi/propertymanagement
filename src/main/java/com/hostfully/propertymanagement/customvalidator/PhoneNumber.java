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
@Pattern(regexp = "^(\\+\\d{1,4})?[-.\\s]?\\(?\\d{1,6}\\)?[-.\\s]?\\d{1,10}[-.\\s]?\\d{1,10}$", message = "Invalid Phone Number.")
@ReportAsSingleViolation
public @interface PhoneNumber {
    String message() default "Invalid Phone Number.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}