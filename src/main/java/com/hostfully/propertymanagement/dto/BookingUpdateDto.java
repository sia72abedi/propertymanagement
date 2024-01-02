package com.hostfully.propertymanagement.dto;


import com.hostfully.propertymanagement.customvalidator.StartDateBeforeEndDate;
import jakarta.validation.constraints.PastOrPresent;

import java.io.Serializable;
import java.time.LocalDate;

@StartDateBeforeEndDate
public record BookingUpdateDto(@PastOrPresent(message = "StartDate Should Not Be Future.") LocalDate startDate,
                               @PastOrPresent(message = "StartDate Should Not Be Future.") LocalDate endDate,
                               GuestDto guest) implements Serializable {
        }