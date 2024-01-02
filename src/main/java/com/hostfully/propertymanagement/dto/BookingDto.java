package com.hostfully.propertymanagement.dto;


import com.hostfully.propertymanagement.customvalidator.EntityExists;
import com.hostfully.propertymanagement.customvalidator.StartDateBeforeEndDate;
import com.hostfully.propertymanagement.entities.Booking;
import com.hostfully.propertymanagement.entities.Property;
import jakarta.validation.constraints.PastOrPresent;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link Booking}
 */
@StartDateBeforeEndDate
public record BookingDto(@PastOrPresent(message = "StartDate Should Not Be Future.") LocalDate startDate,
                         @PastOrPresent(message = "StartDate Should Not Be Future.") LocalDate endDate,
                         @EntityExists(entityType = Property.class,message = "Property Does Not Exist.") Integer propertyId,
                         GuestDto guest) implements Serializable {
}