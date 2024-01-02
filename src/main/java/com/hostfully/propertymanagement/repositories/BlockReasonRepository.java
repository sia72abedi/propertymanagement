package com.hostfully.propertymanagement.repositories;

import com.hostfully.propertymanagement.entities.BlockReason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BlockReasonRepository extends JpaRepository<BlockReason, Integer> {
}