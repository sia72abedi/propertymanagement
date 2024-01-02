package com.hostfully.propertymanagement.repositories;

import com.hostfully.propertymanagement.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
}