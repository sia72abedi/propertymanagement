package com.hostfully.propertymanagement.repositories;

import com.hostfully.propertymanagement.entities.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, Integer> {
}