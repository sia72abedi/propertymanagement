package com.hostfully.propertymanagement.repositories;

import com.hostfully.propertymanagement.dto.BookingGetDto;
import com.hostfully.propertymanagement.entities.Booking;
import com.hostfully.propertymanagement.misc.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    @Transactional
    @Modifying
    @Query("update Booking b set b.bookingStatus = ?1 where b.id = ?2")
    int updateBookingStatusById(BookingStatus bookingStatus, Integer id);
    @Transactional
    @Modifying
    @Query("update Booking b set b.recordStatus = com.hostfully.propertymanagement.misc.RecordStatus.REMOVED " +
            "where b.id = :id and b.recordStatus = com.hostfully.propertymanagement.misc.RecordStatus.EXIST")
    int removeExistById(Integer id);

    @Query("select new com.hostfully.propertymanagement.dto.BookingGetDto(b.startDate,b.endDate,b.property,b.guest) " +
            "from Booking b where b.id = :id and b.recordStatus = com.hostfully.propertymanagement.misc.RecordStatus.EXIST " +
            "and b.bookingStatus = com.hostfully.propertymanagement.misc.BookingStatus.CONFIRMED")
    Optional<BookingGetDto> findActiveAsGetDtoById(int id);
    @Query("select b from Booking b where b.id = :id and b.recordStatus = com.hostfully.propertymanagement.misc.RecordStatus.EXIST " +
            "and b.bookingStatus = com.hostfully.propertymanagement.misc.BookingStatus.CONFIRMED")
    Optional<Booking> findActiveById(int id);
    @Transactional
    @Modifying
    @Query("update Booking b set b.bookingStatus = com.hostfully.propertymanagement.misc.BookingStatus.CONFIRMED " +
            "where b.id = :id and b.recordStatus = com.hostfully.propertymanagement.misc.RecordStatus.EXIST " +
            "and b.bookingStatus = com.hostfully.propertymanagement.misc.BookingStatus.CANCELED")
    int rebookCanceledBookingById(int id);
}