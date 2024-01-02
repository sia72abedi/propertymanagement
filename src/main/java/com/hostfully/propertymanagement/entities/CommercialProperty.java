package com.hostfully.propertymanagement.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
@DiscriminatorValue("Commercial")
public class CommercialProperty extends Property{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private boolean hasFreeWifi;
    @Column boolean hasSafeBox;
    @Column boolean hasAirCondition;
    @Column boolean hasKitchen;
    @Column boolean hasRestRoom;
    @Column
    private int area;
    //some property covered as it's a test only
}
