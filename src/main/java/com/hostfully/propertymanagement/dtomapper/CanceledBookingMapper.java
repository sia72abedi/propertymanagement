package com.hostfully.propertymanagement.dtomapper;

import com.hostfully.propertymanagement.dto.CancelBookingDto;
import com.hostfully.propertymanagement.entities.CanceledBooking;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CanceledBookingMapper {
    @Mapping(source = "reasonId", target = "reason.id")
    CanceledBooking toEntity(CancelBookingDto cancelBookingDto);

    @Mapping(source = "reason.id", target = "reasonId")
    CancelBookingDto toDto(CanceledBooking canceledBooking);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "reasonId", target = "reason.id")
    CanceledBooking partialUpdate(CancelBookingDto cancelBookingDto, @MappingTarget CanceledBooking canceledBooking);
}