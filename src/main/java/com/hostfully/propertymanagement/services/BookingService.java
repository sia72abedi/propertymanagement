package com.hostfully.propertymanagement.services;

import com.hostfully.propertymanagement.controllers.BookingController;
import com.hostfully.propertymanagement.dto.BookingDto;
import com.hostfully.propertymanagement.dto.CancelBookingDto;
import com.hostfully.propertymanagement.dto.BookingUpdateDto;
import com.hostfully.propertymanagement.dto.BookingGetDto;
import com.hostfully.propertymanagement.dto.GuestDto;
import com.hostfully.propertymanagement.dto.Response;
import com.hostfully.propertymanagement.dtomapper.BookingMapper;
import com.hostfully.propertymanagement.dtomapper.CanceledBookingMapper;
import com.hostfully.propertymanagement.dtomapper.GuestMapper;
import com.hostfully.propertymanagement.entities.Booking;
import com.hostfully.propertymanagement.entities.CanceledBooking;
import com.hostfully.propertymanagement.entities.Guest;
import com.hostfully.propertymanagement.exceptions.InvalidInputException;
import com.hostfully.propertymanagement.misc.BookingStatus;
import com.hostfully.propertymanagement.misc.RecordStatus;
import com.hostfully.propertymanagement.repositories.BookingRepository;
import com.hostfully.propertymanagement.repositories.CanceledBookingRepository;
import com.hostfully.propertymanagement.repositories.GuestRepository;
import lombok.RequiredArgsConstructor;
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
    private final GuestRepository guestRepository;
    private final ReservedService reservedService;
    private final GuestMapper guestMapper;

    @Transactional
    public Response bookProperty(BookingDto bookingDto) {
        reservedService.isPropertyReserved(null,bookingDto.propertyId(),bookingDto.startDate(),bookingDto.endDate());
        Booking booking = bookingMapper.toEntity(bookingDto);
        Guest guest = checkIsUserExistAndUpdateIt(bookingDto.guest());
        if(guest != null){
            booking.setGuest(guest);
        }
        booking.setRecordStatus(RecordStatus.EXIST);
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        booking = bookingRepository.save(booking);
        return Response.builder().id(String.valueOf(booking.getId()))
                .href(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookingController.class).getBookingById(booking.getId())).toString())
                .message("Request Processed Successfully.").build();
    }

    @Transactional
    public Response editBookingById(int id, BookingUpdateDto bookingUpdateDto) {
        // a canceled booking will not editable till rebook
        Booking booking = getBookingById(id);
        reservedService.isPropertyReserved(id,booking.getProperty().getId(),bookingUpdateDto.startDate(),bookingUpdateDto.endDate());
        Guest guest = checkIsUserExistAndUpdateIt(bookingUpdateDto.guest());
        if(guest != null){
            booking.setGuest(guest);
        }else {
            booking.setGuest(Guest.builder().build());
        }
        bookingMapper.partialUpdate(bookingUpdateDto,booking);
        bookingRepository.save(booking);
        return Response.builder().id(String.valueOf(booking.getId()))
                .href(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookingController.class).getBookingById(booking.getId())).toString())
                .message("Request Processed Successfully.").build();
    }

    private Guest checkIsUserExistAndUpdateIt(GuestDto guestDto){
        Optional<Guest> optionalGuest = guestRepository.findByGuestEmailAddressOrPhoneNumberOrPassportNo(
                guestDto.guestEmailAddress(),guestDto.guestPhoneNo(),guestDto.guestPassportId());
        if(optionalGuest.isPresent()){
            Guest guest = optionalGuest.get();
            guestMapper.partialUpdate(guestDto,guest);
            return guestRepository.save(guest);
        }
        return null;
    }

    private Booking getBookingById(int id) {
        Optional<Booking> booking = bookingRepository.findActiveById(id);
        return booking.orElseThrow(() -> new InvalidInputException("Booking Does Not Exist."));
    }

    @Transactional
    public Response cancelBookingById(int id, CancelBookingDto cancelBookingDto) {
        Booking booking = getBookingById(id);
        bookingRepository.updateBookingStatusById(BookingStatus.CANCELED,booking.getId());
        CanceledBooking canceledBooking = canceledBookingMapper.toEntity(cancelBookingDto);
        canceledBooking.setBooking(booking);
        canceledBooking.setRecordStatus(RecordStatus.EXIST);
        canceledBookingRepository.save(canceledBooking);
        return Response.builder().message("Request Processed Successfully.").build();
    }

    @Transactional
    public Response rebookCanceledBookingById(int id) {
        int updatedCount = bookingRepository.rebookCanceledBookingById(id);
        if(updatedCount == 0){
            throw new InvalidInputException("There Is No Canceled Booking With Given Id");
        }
        canceledBookingRepository.removeExistingByBookingId(id);
        return Response.builder().id(String.valueOf(id))
                .href(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookingController.class).getBookingById(id)).toString())
                .message("Request Processed Successfully.").build();
    }

    @Transactional
    public Response deleteBookingById(int id) {
        int count = bookingRepository.removeExistById(id);
        if(count == 0){
            throw new InvalidInputException("Booking Does Not Exist(Maybe Removed Previously).");
        }
        canceledBookingRepository.removeExistingByBookingId(id);
        return Response.builder().message("Request Processed Successfully.").build();
    }

    public BookingGetDto getBookingDtoById(int id) {
        Optional<BookingGetDto> getDtoOptional = bookingRepository.findActiveAsGetDtoById(id);
        return getDtoOptional.orElseThrow(() -> new InvalidInputException("Booking Does Not Exist(Canceled Or Removed)."));
    }

}
