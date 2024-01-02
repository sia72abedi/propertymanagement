package com.hostfully.propertymanagement.dto;

import com.hostfully.propertymanagement.entities.Property;

import java.io.Serializable;
import java.time.LocalDate;

public record BlockedGetDto(LocalDate startDate, LocalDate endDate, Property property, Integer reasonId,
                            String message) implements Serializable {
}