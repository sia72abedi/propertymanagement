package com.hostfully.propertymanagement.repositories;

import com.hostfully.propertymanagement.entities.BlockReason;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockReasonRepository extends JpaRepository<BlockReason, Integer> {
}