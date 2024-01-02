package com.hostfully.propertymanagement.controllers;

import com.hostfully.propertymanagement.dto.BlockingDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.hostfully.propertymanagement.dto.Response;
import com.hostfully.propertymanagement.dto.BlockedGetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.hostfully.propertymanagement.services.BlockService;

@RestController
@Tag(name = "block")
public class BlockController {
    private final BlockService blockService;

    @Autowired
    public BlockController(BlockService blockService) {
        this.blockService = blockService;
    }

    @PostMapping(value = "api/v1/block", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> blockingProperty(@RequestBody BlockingDto blockingDto){
        Response response = blockService.blockingProperty(blockingDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PutMapping(value = "api/v1/block/{block-id}", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> updateBlockedById(@PathVariable(value = "block-id") String id,
                                                      @RequestBody BlockingDto blockingDto){
        Response response = blockService.updateBlockedById(id, blockingDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "api/v1/block/{block-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BlockedGetDto> getBlockedById(@PathVariable(value = "block-id") String id){
        BlockedGetDto response = blockService.getBlockedById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "api/v1/block/{block-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> deleteBlockedById(@PathVariable(value = "block-id") String id){
        Response response = blockService.deleteBlockedById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
