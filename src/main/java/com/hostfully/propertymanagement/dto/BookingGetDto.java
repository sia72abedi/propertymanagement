package com.hostfully.propertymanagement.dto;


import com.hostfully.propertymanagement.entities.Guest;
import com.hostfully.propertymanagement.entities.Property;

import java.io.Serializable;
import java.time.LocalDate;

public record BookingGetDto (LocalDate startDate, LocalDate endDate, Property property,
                             Guest guest) implements Serializable {
}
