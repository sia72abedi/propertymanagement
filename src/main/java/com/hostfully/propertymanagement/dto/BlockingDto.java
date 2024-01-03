package com.hostfully.propertymanagement.dto;

import com.hostfully.propertymanagement.customvalidator.EntityExists;
import com.hostfully.propertymanagement.customvalidator.StartDateBeforeEndDate;
import com.hostfully.propertymanagement.entities.Block;
import com.hostfully.propertymanagement.entities.BlockReason;
import com.hostfully.propertymanagement.entities.Property;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link Block}
 */
@StartDateBeforeEndDate
public record BlockingDto(@FutureOrPresent(message = "startDate Should Not Be Past.") LocalDate startDate,
                          @FutureOrPresent(message = "endDate Should Not Be Past.") LocalDate endDate,
                          @EntityExists(entityType = Property.class,message = "Property Does Not Exist.") Integer propertyId,
                          @EntityExists(entityType = BlockReason.class,message = "Reason Does Not Exist.") Integer reasonId,
                          @Size(max = 500,message = "Message Length < 500.") String message) implements Serializable {
}