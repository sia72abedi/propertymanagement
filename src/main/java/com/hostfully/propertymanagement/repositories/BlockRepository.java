package com.hostfully.propertymanagement.repositories;

import com.hostfully.propertymanagement.dto.BlockedGetDto;
import com.hostfully.propertymanagement.entities.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BlockRepository extends JpaRepository<Block, Integer> {
    @Query("select new com.hostfully.propertymanagement.dto.BlockedGetDto(b.startDate,b.endDate,b.property," +
            "b.message,b.reason.reason) from Block b where b.id = :id")
    public Optional<BlockedGetDto> findGetDtoById(Integer id);
}