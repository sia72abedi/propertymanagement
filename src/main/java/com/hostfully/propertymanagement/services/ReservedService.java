package com.hostfully.propertymanagement.services;

import com.hostfully.propertymanagement.exceptions.InvalidInputException;
import com.hostfully.propertymanagement.repositories.ReservingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ReservedService {
    private final ReservingRepository reservingRepository;

    public boolean isPropertyReserved(Integer currentReservationId, Integer propertyId, LocalDate startDate, LocalDate endDate) {
        //canceled booking consider as still reserved till it deleted.
        Boolean isPropertyReserved = reservingRepository.isPropertyReservedByIdAndDateRange(currentReservationId, propertyId, startDate, endDate);
        if(isPropertyReserved != null){
            throw new InvalidInputException("The Property Reserved In The Requested Time Period.");
        }
        return false;
    }
}
