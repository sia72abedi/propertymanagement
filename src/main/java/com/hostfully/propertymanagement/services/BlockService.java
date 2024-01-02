package com.hostfully.propertymanagement.services;

import com.hostfully.propertymanagement.dto.BlockingDto;
import com.hostfully.propertymanagement.dto.BlockedGetDto;
import com.hostfully.propertymanagement.dto.Response;
import com.hostfully.propertymanagement.dtomapper.BlockMapper;
import com.hostfully.propertymanagement.repositories.BlockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlockService {
    private final BlockMapper blockMapper;
    private final BlockRepository blockRepository;
    public Response blockingProperty(BlockingDto blockingDto) {
        return null;
    }

    public Response updateBlockedById(int id, BlockingDto blockingDto) {
        return null;
    }

    public BlockedGetDto getBlockedById(int id) {
        return null;
    }

    public Response deleteBlockedById(int id) {
        return null;
    }
}
