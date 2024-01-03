package com.hostfully.propertymanagement.repositories;

import com.hostfully.propertymanagement.entities.CanceledBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CanceledBookingRepository extends JpaRepository<CanceledBooking, Integer> {
    @Transactional
    @Modifying
    @Query("update CanceledBooking cb set cb.recordStatus = com.hostfully.propertymanagement.misc.RecordStatus.REMOVED" +
            " where cb.booking.id = :id and cb.recordStatus = com.hostfully.propertymanagement.misc.RecordStatus.EXIST")
    public int removeExistingByBookingId(Integer id);
}