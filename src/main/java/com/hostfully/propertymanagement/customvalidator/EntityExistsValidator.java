package com.hostfully.propertymanagement.customvalidator;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class EntityExistsValidator implements ConstraintValidator<EntityExists, Object> {

    private final EntityManager entityManager;
    private Class<?> entityType;

    @Autowired
    public EntityExistsValidator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void initialize(EntityExists constraintAnnotation) {
        this.entityType = constraintAnnotation.entityType();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return entityManager.createQuery("SELECT COUNT(e) FROM " + entityType.getSimpleName() + " e WHERE e.id = :id", Long.class)
                .setParameter("id", value)
                .getSingleResult() > 0;
    }
}