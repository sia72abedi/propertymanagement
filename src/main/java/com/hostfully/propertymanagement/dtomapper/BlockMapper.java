package com.hostfully.propertymanagement.dtomapper;

import com.hostfully.propertymanagement.dto.BlockingDto;
import com.hostfully.propertymanagement.dto.BlockedGetDto;
import com.hostfully.propertymanagement.entities.Block;
import org.mapstruct.BeanMapping;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BlockMapper {
    @Mapping(source = "reasonId", target = "reason.id")
    @Mapping(source = "propertyId", target = "property.id")
    Block toEntity(BlockingDto blockingDto);

    @InheritInverseConfiguration(name = "toEntity")
    BlockingDto toDto(Block block);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Block partialUpdate(BlockingDto blockingDto, @MappingTarget Block block);


    Block toEntity(BlockedGetDto blockedGetDto);

    @InheritInverseConfiguration(name = "toEntity")
    BlockedGetDto toGetDto(Block block);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Block partialUpdate(BlockedGetDto blockedGetDto, @MappingTarget Block block);
}