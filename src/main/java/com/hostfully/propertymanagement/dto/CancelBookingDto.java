package com.hostfully.propertymanagement.dto;


import com.hostfully.propertymanagement.customvalidator.EntityExists;
import com.hostfully.propertymanagement.entities.CanceledBooking;
import com.hostfully.propertymanagement.entities.Property;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link CanceledBooking}
 */
public record CancelBookingDto(@EntityExists(entityType = Property.class,message = "Reason Does Not Exist.")Integer reasonId,
                               @Size(max = 500, message = "Message Length <= 500") String message) implements Serializable {
}