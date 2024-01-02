package com.hostfully.propertymanagement.repositories;

import com.hostfully.propertymanagement.dto.BookingGetDto;
import com.hostfully.propertymanagement.entities.Booking;
import com.hostfully.propertymanagement.misc.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    @Transactional
    @Modifying
    @Query("update Booking b set b.recordStatus = ?1 where b.id = ?2")
    int updateRecordStatusById(RecordStatus recordStatus, Integer id);

    @Query("select new com.hostfully.propertymanagement.dto.BookingGetDto(b.startDate,b.endDate,b.property,b.guest) " +
            "from Booking b where b.id = :id and b.recordStatus = com.hostfully.propertymanagement.misc.RecordStatus.EXIST")
    Optional<BookingGetDto> findActiveById(int id);
}