package com.hostfully.propertymanagement.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String guestFirstName;
    @Column
    private String guestLastName;
    @Column
    private String guestEmailAddress;
    @Column
    private String guestPhoneNo;
    @Column
    private String guestPassportId;
}
