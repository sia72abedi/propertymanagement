package com.hostfully.propertymanagement.customvalidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;

public class startDateBeforeEndDateValidator implements
        ConstraintValidator<StartDateBeforeEndDate, Object> {

    @Override
    public void initialize(StartDateBeforeEndDate startDateBeforeEndDate) {
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext cxt) {
        if (object == null) {
            return true;
        }
        Class<?> clazz = object.getClass();
        try {
            Field startDateField = clazz.getDeclaredField("startDate");
            Field endDateField = clazz.getDeclaredField("endDate");

            startDateField.setAccessible(true);
            endDateField.setAccessible(true);

            Object startDateValue = startDateField.get(object);
            Object endDateValue = endDateField.get(object);

            if (startDateValue instanceof Comparable && endDateValue instanceof Comparable) {
                Comparable<Object> startDate = (Comparable<Object>) startDateValue;
                Comparable<Object> endDate = (Comparable<Object>) endDateValue;

                return startDate.compareTo(endDate) <= 0;
            } else {
                return false;
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

}