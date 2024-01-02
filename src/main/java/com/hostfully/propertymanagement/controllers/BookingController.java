package com.hostfully.propertymanagement.controllers;

import com.hostfully.propertymanagement.dto.BookingDto;
import com.hostfully.propertymanagement.dto.CancelBookingDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.hostfully.propertymanagement.dto.Response;
import com.hostfully.propertymanagement.dto.BookingUpdateDto;
import com.hostfully.propertymanagement.dto.BookingGetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.hostfully.propertymanagement.services.BookingService;

@RestController
@Tag(name = "booking")
public class BookingController {
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> bookProperty(@RequestBody BookingDto bookingDto){
        Response response = bookingService.bookProperty(bookingDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PatchMapping(value = "api/v1/booking/{booking-id}", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> editBookingById(@PathVariable(value = "booking-id") String id,
                                                    @RequestBody BookingUpdateDto bookingUpdateDto){
        Response response = bookingService.editBookingById(id, bookingUpdateDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping(value = "api/v1/booking/cancel/{booking-id}", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> cancelBookingById(@PathVariable(value = "booking-id") String id,
                                                      @RequestBody CancelBookingDto cancelBookingDto){
        Response response = bookingService.cancelBookingById(id, cancelBookingDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping(value = "api/v1/booking/rebook/{cancel-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> rebookCanceledBookingById(@PathVariable(value = "cancel-id") String id){
        Response response = bookingService.rebookCanceledBookingById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "api/v1/booking/{booking-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> deleteBookingById(@PathVariable(value = "booking-id") String id){
        Response response = bookingService.deleteBookingById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "api/v1/booking/{booking-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookingGetDto> getBookingById(@PathVariable(value = "booking-id") String id){
        BookingGetDto response = bookingService.getBookingById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
