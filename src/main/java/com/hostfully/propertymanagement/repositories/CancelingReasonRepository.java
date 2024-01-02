package com.hostfully.propertymanagement.repositories;

import com.hostfully.propertymanagement.entities.CancelingReason;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CancelingReasonRepository extends JpaRepository<CancelingReason, Integer> {
}