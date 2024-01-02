package com.hostfully.propertymanagement.customvalidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EntityExistsValidator.class)
public @interface EntityExists {
    String message() default "Entity Does Not Exist.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<?> entityType();
}