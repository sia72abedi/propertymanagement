package com.hostfully.propertymanagement.dto;


import com.hostfully.propertymanagement.entities.CanceledBooking;

import java.io.Serializable;

/**
 * DTO for {@link CanceledBooking}
 */
public record CancelBookingDto(Integer reasonId, String message) implements Serializable {
}