package com.hostfully.propertymanagement.services;

import com.hostfully.propertymanagement.dto.BookingDto;
import com.hostfully.propertymanagement.dto.CancelBookingDto;
import com.hostfully.propertymanagement.dto.BookingUpdateDto;
import com.hostfully.propertymanagement.dto.BookingGetDto;
import com.hostfully.propertymanagement.dto.Response;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    public Response bookProperty(BookingDto bookingDto) {
        return null;
    }

    public Response editBookingById(String id, BookingUpdateDto bookingUpdateDto) {
        return null;
    }

    public Response cancelBookingById(String id, CancelBookingDto cancelBookingDto) {
        return null;
    }

    public Response rebookCanceledBookingById(String id) {
        return null;
    }

    public Response deleteBookingById(String id) {
        return null;
    }

    public BookingGetDto getBookingById(String id) {
        return null;
    }
}
