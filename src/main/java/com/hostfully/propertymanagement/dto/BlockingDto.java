package com.hostfully.propertymanagement.dto;

import com.hostfully.propertymanagement.entities.Block;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link Block}
 */
public record BlockingDto(@PastOrPresent(message = "StartDate Should Not Be Future.") LocalDate startDate,
                          @PastOrPresent(message = "StartDate Should Not Be Future.") LocalDate endDate,
                          Integer propertyId, Integer reasonId,
                          @Size(max = 300,message = "Message Should Length < 300.") String message) implements Serializable {
}