package com.hostfully.propertymanagement.repositories;

import com.hostfully.propertymanagement.entities.CanceledBooking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CanceledBookingRepository extends JpaRepository<CanceledBooking, Integer> {
}