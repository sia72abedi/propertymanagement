package com.hostfully.propertymanagement.entities;


import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
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
@JsonTypeName("Hotel")
public class HotelProperty extends Property{
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
