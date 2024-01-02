package com.hostfully.propertymanagement.dto;


import com.hostfully.propertymanagement.customvalidator.PassportNumber;
import com.hostfully.propertymanagement.customvalidator.PhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.hostfully.propertymanagement.entities.Guest}
 */
@Value
public class GuestDto implements Serializable {
    @Size(max = 50, message = "Guest First Name Length <= 50")
    String guestFirstName;
    @Size(max = 100, message = "Guest Last Name Length <= 100")
    String guestLastName;
    @Email
    String guestEmailAddress;
    @PhoneNumber
    String guestPhoneNo;
    @PassportNumber
    String guestPassportId;
}