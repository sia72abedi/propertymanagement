package com.hostfully.propertymanagement.repositories;

import com.hostfully.propertymanagement.entities.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Integer> {
}