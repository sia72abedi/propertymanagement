package com.hostfully.propertymanagement.repositories;

import com.hostfully.propertymanagement.entities.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GuestRepository extends JpaRepository<Guest, Integer> {
    @Query("select g from Guest g where g.guestEmailAddress = :guestEmailAddress or g.guestPhoneNo = :guestPhoneNo or g.guestPassportId = :guestPassportId")
    Optional<Guest> findByGuestEmailAddressOrPhoneNumberOrPassportNo(String guestEmailAddress, String guestPhoneNo, String guestPassportId);
}