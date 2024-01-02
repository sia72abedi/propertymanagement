package com.hostfully.propertymanagement.dto;


import com.hostfully.propertymanagement.customvalidator.EntityExists;
import com.hostfully.propertymanagement.customvalidator.StartDateBeforeEndDate;
import com.hostfully.propertymanagement.entities.Booking;
import com.hostfully.propertymanagement.entities.Property;
import jakarta.validation.constraints.FutureOrPresent;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link Booking}
 */
@StartDateBeforeEndDate
public record BookingDto(@FutureOrPresent(message = "startDate Should Not Be Past.") LocalDate startDate,
                         @FutureOrPresent(message = "endDate Should Not Be Past.") LocalDate endDate,
                         @EntityExists(entityType = Property.class,message = "Property Does Not Exist.") Integer propertyId,
                         GuestDto guest) implements Serializable {
}