package com.hostfully.propertymanagement.repositories;

import com.hostfully.propertymanagement.dto.BlockedGetDto;
import com.hostfully.propertymanagement.entities.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface BlockRepository extends JpaRepository<Block, Integer> {
    @Transactional
    @Modifying
    @Query("update Block b set b.recordStatus = com.hostfully.propertymanagement.misc.RecordStatus.REMOVED " +
            "where b.id = :id and b.recordStatus = com.hostfully.propertymanagement.misc.RecordStatus.EXIST")
    int removeExistingById(Integer id);
    @Query("select new com.hostfully.propertymanagement.dto.BlockedGetDto(b.startDate,b.endDate,b.property," +
            "b.message,b.reason.reason) from Block b where b.id = :id " +
            "and b.recordStatus = com.hostfully.propertymanagement.misc.RecordStatus.EXIST")
    public Optional<BlockedGetDto> findGetDtoById(Integer id);
}