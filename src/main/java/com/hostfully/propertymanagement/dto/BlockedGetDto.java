package com.hostfully.propertymanagement.dto;

import com.hostfully.propertymanagement.customvalidator.StartDateBeforeEndDate;
import com.hostfully.propertymanagement.entities.Property;

import java.io.Serializable;
import java.time.LocalDate;

@StartDateBeforeEndDate
public record BlockedGetDto(LocalDate startDate, LocalDate endDate, Property property,
                            String message, String reason) implements Serializable {
}