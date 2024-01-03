package com.hostfully.propertymanagement.services;

import com.hostfully.propertymanagement.controllers.BlockController;
import com.hostfully.propertymanagement.dto.BlockingDto;
import com.hostfully.propertymanagement.dto.BlockedGetDto;
import com.hostfully.propertymanagement.dto.Response;
import com.hostfully.propertymanagement.dtomapper.BlockMapper;
import com.hostfully.propertymanagement.entities.Block;
import com.hostfully.propertymanagement.exceptions.InvalidInputException;
import com.hostfully.propertymanagement.misc.GlobalMessages;
import com.hostfully.propertymanagement.misc.RecordStatus;
import com.hostfully.propertymanagement.repositories.BlockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlockService {
    private final BlockMapper blockMapper;
    private final BlockRepository blockRepository;
    private final ReservedService reservedService;
    public Response blockingProperty(BlockingDto blockingDto) {
        reservedService.isPropertyReserved(null,blockingDto.propertyId(),blockingDto.startDate(),blockingDto.endDate());
        Block block = blockMapper.toEntity(blockingDto);
        block.setRecordStatus(RecordStatus.EXIST);
        block = blockRepository.save(block);
        return Response.builder().id(String.valueOf(block.getId()))
                .href(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BlockController.class).getBlockedById(block.getId())).toString())
                .message(GlobalMessages.SUCCESS).build();
    }

    public Response updateBlockedById(int id, BlockingDto blockingDto) {
        reservedService.isPropertyReserved(id, blockingDto.propertyId(),blockingDto.startDate(),blockingDto.endDate());
        Block block = blockMapper.toEntity(blockingDto);
        block.setId(id);
        block.setRecordStatus(RecordStatus.EXIST);
        blockRepository.save(block);
        return Response.builder().id(String.valueOf(id))
                .href(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BlockController.class).getBlockedById(block.getId())).toString())
                .message(GlobalMessages.SUCCESS).build();
    }

    public BlockedGetDto getBlockedById(int id) {
        Optional<BlockedGetDto> optionalBlockedGetDto = blockRepository.findGetDtoById(id);
        return optionalBlockedGetDto.orElseThrow(() -> new InvalidInputException("Block Does Not Exist."));
    }

    public Response deleteBlockedById(int id) {
        int count = blockRepository.removeExistingById(id);
        if (count == 0){
            throw new InvalidInputException("Block Does Not Exist.");
        }
        return Response.builder().message(GlobalMessages.SUCCESS).build();
    }
}
