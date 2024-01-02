package com.hostfully.propertymanagement.repositories;

import com.hostfully.propertymanagement.entities.CanceledBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CanceledBookingRepository extends JpaRepository<CanceledBooking, Integer> {
    @Query("select cb from CanceledBooking cb where cb.booking.id = :id")
    public CanceledBooking getByBookingId(Integer id);
}