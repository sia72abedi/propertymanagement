package com.hostfully.propertymanagement.dtomapper;

import com.hostfully.propertymanagement.dto.GuestDto;
import com.hostfully.propertymanagement.entities.Guest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)public interface GuestMapper {
    Guest toEntity(GuestDto guestDto);

    GuestDto toDto(Guest guest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)Guest partialUpdate(GuestDto guestDto, @MappingTarget Guest guest);
}