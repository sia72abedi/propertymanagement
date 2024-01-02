package com.hostfully.propertymanagement.dto;


import com.hostfully.propertymanagement.entities.Booking;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link Booking}
 */
public record BookingDto(LocalDate startDate, LocalDate endDate, Integer propertyId,
                         GuestDto guest) implements Serializable {
}