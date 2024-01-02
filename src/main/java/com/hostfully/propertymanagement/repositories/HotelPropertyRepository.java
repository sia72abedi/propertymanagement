package com.hostfully.propertymanagement.repositories;

import com.hostfully.propertymanagement.entities.HotelProperty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelPropertyRepository extends JpaRepository<HotelProperty, Integer> {
}