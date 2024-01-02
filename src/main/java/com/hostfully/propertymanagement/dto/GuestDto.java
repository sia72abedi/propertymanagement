package com.hostfully.propertymanagement.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.hostfully.propertymanagement.entities.Guest}
 */
@Value
public class GuestDto implements Serializable {
    String guestFirstName;
    String guestLastName;
    String guestEmailAddress;
    String guestPhoneNo;
    String guestPassportId;
}