package com.hostfully.propertymanagement.services;

import com.hostfully.propertymanagement.controllers.BookingController;
import com.hostfully.propertymanagement.dto.BookingDto;
import com.hostfully.propertymanagement.dto.CancelBookingDto;
import com.hostfully.propertymanagement.dto.BookingUpdateDto;
import com.hostfully.propertymanagement.dto.BookingGetDto;
import com.hostfully.propertymanagement.dto.Response;
import com.hostfully.propertymanagement.dtomapper.BookingMapper;
import com.hostfully.propertymanagement.dtomapper.CanceledBookingMapper;
import com.hostfully.propertymanagement.entities.Booking;
import com.hostfully.propertymanagement.entities.CanceledBooking;
import com.hostfully.propertymanagement.exceptions.InvalidInputException;
import com.hostfully.propertymanagement.misc.BookingStatus;
import com.hostfully.propertymanagement.misc.RecordStatus;
import com.hostfully.propertymanagement.repositories.BookingRepository;
import com.hostfully.propertymanagement.repositories.CanceledBookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final CanceledBookingMapper canceledBookingMapper;
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final CanceledBookingRepository canceledBookingRepository;

    public Response bookProperty(BookingDto bookingDto) {
        Booking booking = bookingMapper.toEntity(bookingDto);
        booking = bookingRepository.save(booking);
        return Response.builder().id(String.valueOf(booking.getId()))
                .href(WebMvcLinkBuilder.linkTo(BookingController.class).slash(booking.getId()).withSelfRel().toString())
                .message("Request Processed Successfully.").build();
    }

    public Response editBookingById(int id, BookingUpdateDto bookingUpdateDto) {
        Booking booking = bookingRepository.getReferenceById(id);
        BeanUtils.copyProperties(bookingUpdateDto,booking);
        bookingRepository.save(booking);
        return Response.builder().id(String.valueOf(booking.getId()))
                .href(WebMvcLinkBuilder.linkTo(BookingController.class).slash(booking.getId()).withSelfRel().toString())
                .message("Request Processed Successfully.").build();
    }

    @Transactional
    public Response cancelBookingById(int id, CancelBookingDto cancelBookingDto) {
        Booking booking = bookingRepository.getReferenceById(id);
        booking.setBookingStatus(BookingStatus.CANCELED);
        bookingRepository.save(booking);
        CanceledBooking canceledBooking = canceledBookingMapper.toEntity(cancelBookingDto);
        canceledBookingRepository.save(canceledBooking);
        return Response.builder().id(String.valueOf(booking.getId()))
                .href(WebMvcLinkBuilder.linkTo(BookingController.class).slash(booking.getId()).withSelfRel().toString())
                .message("Request Processed Successfully.").build();
    }

    public Response rebookCanceledBookingById(int id) {
        CanceledBooking canceledBooking = canceledBookingRepository.getByBookingId(id);
        canceledBooking.setRecordStatus(RecordStatus.REMOVED);
        return Response.builder().id(String.valueOf(id))
                .href(WebMvcLinkBuilder.linkTo(BookingController.class).slash(id).withSelfRel().toString())
                .message("Request Processed Successfully.").build();
    }

    public Response deleteBookingById(int id) {
        bookingRepository.updateRecordStatusById(RecordStatus.REMOVED,id);
        return Response.builder().message("Request Processed Successfully.").build();
    }

    public BookingGetDto getBookingById(int id) {
        Optional<BookingGetDto> getDtoOptional = bookingRepository.findActiveById(id);
        return getDtoOptional.orElseThrow(() -> new InvalidInputException("Booking Does Not Exist."));

    }
}
