package com.hostfully.propertymanagement.services;

import com.hostfully.propertymanagement.customassertion.BlockAssertion;
import com.hostfully.propertymanagement.customassertion.BlockingDtoAssertion;
import com.hostfully.propertymanagement.customassertion.ResponseAssertion;
import com.hostfully.propertymanagement.dto.BlockingDto;
import com.hostfully.propertymanagement.dto.Response;
import com.hostfully.propertymanagement.dtomapper.BlockMapper;
import com.hostfully.propertymanagement.entities.Block;
import com.hostfully.propertymanagement.entities.BlockReason;
import com.hostfully.propertymanagement.entities.HotelProperty;
import com.hostfully.propertymanagement.entities.Property;
import com.hostfully.propertymanagement.exceptions.InvalidInputException;
import com.hostfully.propertymanagement.repositories.BlockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BlockServiceTest {
    @Mock
    BlockRepository blockRepository;
    @Mock
    BlockMapper blockMapper;
    @InjectMocks
    BlockService blockService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void blockingProperty_RegularCallMapper() {
        ArgumentCaptor<BlockingDto> blockingDtoArgumentCaptor = ArgumentCaptor.forClass(BlockingDto.class);
        BlockingDto blockingDto = new BlockingDto(LocalDate.of(2023,1,1),
                LocalDate.now(),1,1,"test");
        blockService.blockingProperty(blockingDto);
        verify(blockMapper,times(1)).toEntity(blockingDtoArgumentCaptor.capture());
        BlockingDtoAssertion.assertThat(blockingDtoArgumentCaptor.getValue()).isEquivalent(blockingDto);
    }
    @Test
    void blockingProperty_RegularCallRepo() {
        ArgumentCaptor<Block> blockArgumentCaptor = ArgumentCaptor.forClass(Block.class);
        BlockingDto blockingDto = new BlockingDto(LocalDate.of(2023,1,1),
                LocalDate.now(),1,1,"test");
        Response actualResponse = blockService.blockingProperty(blockingDto);
        verify(blockRepository,times(1)).save(blockArgumentCaptor.capture());
        when(blockRepository.save(any(Block.class))).thenReturn(Block.builder().id(10).build());
        BlockAssertion.assertThat(blockArgumentCaptor.getValue()).isEquivalent(blockingDto);
        ResponseAssertion.assertThat(actualResponse).isEquivalent(
                Response.builder().message("Request Processed Successfully.")
                        .id("10").href("/api/v1/block/10").build());
    }
    @Test
    void updateBlockedById_RegularCallMapper() {
        Block block = Block.builder().message("test").build();
        when(blockMapper.toEntity(any(BlockingDto.class))).thenReturn(block);
        ArgumentCaptor<BlockingDto> blockingDtoArgumentCaptor = ArgumentCaptor.forClass(BlockingDto.class);
        BlockingDto blockingDto = new BlockingDto(LocalDate.of(2023,1,1),
                LocalDate.now(),1,1,"test");
        int id = 10;
        blockService.updateBlockedById(id,blockingDto);
        verify(blockMapper,times(1)).toEntity(blockingDtoArgumentCaptor.capture());
        BlockingDtoAssertion.assertThat(blockingDtoArgumentCaptor.getValue()).isEquivalent(blockingDto);
    }
    @Test
    void updateBlockedById_RegularCallRepo() {
        BlockReason reason = new BlockReason(1,"reason");
        Property property = HotelProperty.builder().id(1).build();
        Block block = Block.builder().reason(reason).endDate(LocalDate.now())
                .startDate(LocalDate.of(2023,1,1)).property(property).message("test2").build();
        when(blockMapper.toEntity(any(BlockingDto.class))).thenReturn(block);
        ArgumentCaptor<Block> blockArgumentCaptor = ArgumentCaptor.forClass(Block.class);
        BlockingDto blockingDto = new BlockingDto(LocalDate.of(2023,1,1),
                LocalDate.now(),1,1,"test2");
        when(blockRepository.save(any(Block.class))).thenReturn(Block.builder().id(10).build());
        int id = 10;
        Response actualResponse = blockService.updateBlockedById(id,blockingDto);
        verify(blockRepository,times(1)).save(blockArgumentCaptor.capture());
        BlockAssertion.assertThat(blockArgumentCaptor.getValue()).isEquivalent(blockingDto);
        ResponseAssertion.assertThat(actualResponse).isEquivalent(
                Response.builder().message("Request Processed Successfully.")
                        .id("10").href("/api/v1/block/10").build());
    }
    @Test
    void getBlockedById_RegularCallRepo() {
        ArgumentCaptor<Integer> idArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        int id = 10;
        blockService.getBlockedById(id);
        verify(blockRepository,times(1)).getReferenceById(idArgumentCaptor.capture());

        Assertions.assertEquals(idArgumentCaptor.getValue(),id);
    }
    @Test
    void getBlockedById_EntityNotExist() {
        ArgumentCaptor<Integer> idArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        int id = 10;
        InvalidInputException exception = assertThrows(InvalidInputException.class,() -> blockService.getBlockedById(id));
        assertEquals("Block Does Not Exist.",exception.getMessage());
    }


    @Test
    void deleteBlockedById() {
        ArgumentCaptor<Integer> idArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        int blockId = 10;
        Response actualResponse = blockService.deleteBlockedById(blockId);
        verify(blockRepository,times(1)).deleteById(idArgumentCaptor.capture());
        Assertions.assertEquals(idArgumentCaptor.getValue(),10);
        ResponseAssertion.assertThat(actualResponse).hasMessage("Request Processed Successfully.");
    }
}