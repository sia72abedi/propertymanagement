package com.hostfully.propertymanagement.repositories;

import com.hostfully.propertymanagement.entities.Reserving;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservingRepository extends JpaRepository<Reserving, Integer> {
}