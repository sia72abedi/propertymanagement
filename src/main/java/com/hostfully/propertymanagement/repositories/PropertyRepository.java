package com.hostfully.propertymanagement.repositories;

import com.hostfully.propertymanagement.entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Integer> {
}