package com.hostfully.propertymanagement.services;

import com.hostfully.propertymanagement.dto.BlockingDto;
import com.hostfully.propertymanagement.dto.BlockedGetDto;
import com.hostfully.propertymanagement.dto.Response;
import com.hostfully.propertymanagement.dtomapper.BlockMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlockService {
    private final BlockMapper blockMapper;
    public Response blockingProperty(BlockingDto blockingDto) {
        return null;
    }

    public Response updateBlockedById(String id, BlockingDto blockingDto) {
        return null;
    }

    public BlockedGetDto getBlockedById(String id) {
        return null;
    }

    public Response deleteBlockedById(String id) {
        return null;
    }
}
