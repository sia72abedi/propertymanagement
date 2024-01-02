package com.hostfully.propertymanagement.dto;


import java.io.Serializable;
import java.time.LocalDate;

public record BookingUpdateDto(LocalDate startDate, LocalDate endDate, GuestDto guest) implements Serializable {
        }