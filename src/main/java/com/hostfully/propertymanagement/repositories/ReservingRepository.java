package com.hostfully.propertymanagement.repositories;

import com.hostfully.propertymanagement.entities.Reserving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface ReservingRepository extends JpaRepository<Reserving, Integer> {
    @Query("select true from Reserving r where (:currentReservationId is null or r.id != :currentReservationId)" +
            " and r.property.id = :propertyId and" +
            " (r.startDate = :startDate or r.startDate < :endDate and r.endDate > :startDate)")// start date is for the reserved as guest come at 12 o'clock, but end date is next reserve
    Boolean isPropertyReservedByIdAndDateRange(Integer currentReservationId, Integer propertyId, LocalDate startDate, LocalDate endDate);
}