package com.hostfully.propertymanagement.dtomapper;

import com.hostfully.propertymanagement.dto.BookingDto;
import com.hostfully.propertymanagement.dto.BookingUpdateDto;
import com.hostfully.propertymanagement.dto.BookingGetDto;
import com.hostfully.propertymanagement.entities.Booking;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookingMapper {
    @Mapping(source = "propertyId", target = "property.id")
    Booking toEntity(BookingDto bookingDto);
    Booking toEntity(BookingGetDto bookingGetDto);
    Booking toEntity(BookingUpdateDto bookingUpdateDto);
    @Mapping(source = "property.id", target = "propertyId")
    BookingDto toBookingDto(Booking booking);
    BookingGetDto toGetDto(Booking booking);
    BookingGetDto toUpdateDto(Booking booking);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "propertyId", target = "property.id")
    Booking partialUpdate(BookingDto bookingDto, @MappingTarget Booking booking);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Booking partialUpdate(BookingGetDto bookingGetDto, @MappingTarget Booking booking);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Booking partialUpdate(BookingUpdateDto bookingUpdateDto, @MappingTarget Booking booking);
}