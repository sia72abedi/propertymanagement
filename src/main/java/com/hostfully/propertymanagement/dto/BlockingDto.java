package com.hostfully.propertymanagement.dto;

import com.hostfully.propertymanagement.customvalidator.EntityExists;
import com.hostfully.propertymanagement.customvalidator.StartDateBeforeEndDate;
import com.hostfully.propertymanagement.entities.Block;
import com.hostfully.propertymanagement.entities.Property;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link Block}
 */
@StartDateBeforeEndDate
public record BlockingDto(@PastOrPresent(message = "StartDate Should Not Be Future.") LocalDate startDate,
                          @PastOrPresent(message = "StartDate Should Not Be Future.") LocalDate endDate,
                          @EntityExists(entityType = Property.class,message = "Property Does Not Exist.") Integer propertyId,
                          @EntityExists(entityType = Property.class,message = "Reason Does Not Exist.") Integer reasonId,
                          @Size(max = 500,message = "Message Length < 500.") String message) implements Serializable {
}