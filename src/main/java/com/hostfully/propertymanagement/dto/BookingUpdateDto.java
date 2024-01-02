package com.hostfully.propertymanagement.dto;


import com.hostfully.propertymanagement.customvalidator.StartDateBeforeEndDate;
import jakarta.validation.constraints.FutureOrPresent;

import java.io.Serializable;
import java.time.LocalDate;

@StartDateBeforeEndDate
public record BookingUpdateDto(@FutureOrPresent(message = "startDate Should Not Be Past.") LocalDate startDate,
                               @FutureOrPresent(message = "endDate Should Not Be Past.") LocalDate endDate,
                               GuestDto guest) implements Serializable {
        }