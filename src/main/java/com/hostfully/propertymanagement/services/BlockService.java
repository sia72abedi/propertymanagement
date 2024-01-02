package com.hostfully.propertymanagement.services;

import com.hostfully.propertymanagement.controllers.BlockController;
import com.hostfully.propertymanagement.dto.BlockingDto;
import com.hostfully.propertymanagement.dto.BlockedGetDto;
import com.hostfully.propertymanagement.dto.Response;
import com.hostfully.propertymanagement.dtomapper.BlockMapper;
import com.hostfully.propertymanagement.entities.Block;
import com.hostfully.propertymanagement.exceptions.InvalidInputException;
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
    public Response blockingProperty(BlockingDto blockingDto) {
        Block block = blockMapper.toEntity(blockingDto);
        block = blockRepository.save(block);
        return Response.builder().id(String.valueOf(block.getId()))
                .href(WebMvcLinkBuilder.linkTo(BlockController.class).slash(block.getId()).withSelfRel().toString())
                .message("Request Processed Successfully.").build();
    }

    public Response updateBlockedById(int id, BlockingDto blockingDto) {
        Block block = blockMapper.toEntity(blockingDto);
        block.setId(id);
        blockRepository.save(block);
        return Response.builder().id(String.valueOf(id))
                .href(WebMvcLinkBuilder.linkTo(BlockController.class).slash(id).withSelfRel().toString())
                .message("Request Processed Successfully.").build();
    }

    public BlockedGetDto getBlockedById(int id) {
        Optional<BlockedGetDto> optionalBlockedGetDto = blockRepository.findGetDtoById(id);
        return optionalBlockedGetDto.orElseThrow(() -> new InvalidInputException("Block Does Not Exist."));
    }

    public Response deleteBlockedById(int id) {
        blockRepository.updateStatusById(RecordStatus.REMOVED,id);
        return Response.builder().message("Request Processed Successfully.").build();
    }
}
