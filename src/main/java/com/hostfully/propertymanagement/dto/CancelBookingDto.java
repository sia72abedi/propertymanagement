package com.hostfully.propertymanagement.dto;


import com.hostfully.propertymanagement.entities.CanceledBooking;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link CanceledBooking}
 */
public record CancelBookingDto(Integer reasonId,
                               @Size(max = 500, message = "Message Length <= 500") String message) implements Serializable {
}