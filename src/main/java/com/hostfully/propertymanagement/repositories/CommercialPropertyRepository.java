package com.hostfully.propertymanagement.repositories;

import com.hostfully.propertymanagement.entities.CommercialProperty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommercialPropertyRepository extends JpaRepository<CommercialProperty, Integer> {
}