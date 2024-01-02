package com.hostfully.propertymanagement.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@DiscriminatorValue("Hotel")
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class HotelProperty extends Property{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private int roomCount;
    @Column
    private boolean hasFreeWifi;
    @Column
    boolean hasSafeBox;
    @Column
    private boolean hasOutdoorSwimmingPool;
    @Column
    private boolean hasAirCondition;
    @Column
    private boolean hasDailyHouseKeeping;
    @Column
    private boolean hasFitnessCenter;
    @Column
    private boolean hasBar;
    @Column
    private int area;
    //some property covered as it's a test only.
}
