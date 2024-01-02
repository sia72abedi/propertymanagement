package com.hostfully.propertymanagement.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hostfully.propertymanagement.customassertion.BlockedGetDtoAssertion;
import com.hostfully.propertymanagement.customassertion.BlockingDtoAssertion;
import com.hostfully.propertymanagement.customassertion.ResponseAssertion;
import com.hostfully.propertymanagement.customvalidator.EntityExistsValidator;
import com.hostfully.propertymanagement.dto.BlockedGetDto;
import com.hostfully.propertymanagement.dto.BlockingDto;
import com.hostfully.propertymanagement.dto.Response;
import com.hostfully.propertymanagement.entities.BlockReason;
import com.hostfully.propertymanagement.entities.HotelProperty;
import com.hostfully.propertymanagement.entities.Property;
import com.hostfully.propertymanagement.services.BlockService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@WebMvcTest(controllers = BlockController.class)
class BlockControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private BlockService blockService;
    @MockBean
    private EntityExistsValidator entityExistsValidator;

    @Test
    void blockingProperty_Regular() throws Exception {
        when(blockService.blockingProperty(any(BlockingDto.class))).thenReturn(Response.builder()
                .message("Request Processed Successfully.")
                .href("/api/v1/block/10")
                .id("10").build());
        ArgumentCaptor<BlockingDto> blockingDtoArgumentCaptor = ArgumentCaptor.forClass(BlockingDto.class);
        when(entityExistsValidator.isValid(any(),any())).thenReturn(true);
        BlockingDto blockingDto = new BlockingDto(LocalDate.of(2023,1,1),
                LocalDate.now(),1,1,"test");
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/block")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(blockingDto)))
                .andReturn();
        assertEquals(201,mvcResult.getResponse().getStatus());
        Response actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Response.class);
        verify(blockService,times(1)).blockingProperty(blockingDtoArgumentCaptor.capture());
        BlockingDtoAssertion.assertThat(blockingDtoArgumentCaptor.getValue()).isEquivalent(blockingDto);
        ResponseAssertion.assertThat(actualResponse).hasMessage("Request Processed Successfully.");
        ResponseAssertion.assertThat(actualResponse).isHrefContain("api/v1/block");
    }
    @Test
    void blockingProperty_startDateFuture() throws Exception {
        when(entityExistsValidator.isValid(any(),any())).thenReturn(true);
        BlockingDto blockingDto = new BlockingDto(LocalDate.of(2050,1,1),
                LocalDate.now(),1,1,"test");
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/block")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(blockingDto)))
                .andReturn();
        assertEquals(400,mvcResult.getResponse().getStatus());
        Response actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Response.class);
        ResponseAssertion.assertThat(actualResponse).isMessageContain("startDate Should Not Be Future");
        ResponseAssertion.assertThat(actualResponse).isIdNullOrEmpty();
        ResponseAssertion.assertThat(actualResponse).isHrefNullOrEmpty();
    }

    @Test
    void blockingProperty_endDateFuture() throws Exception {
        when(entityExistsValidator.isValid(any(),any())).thenReturn(true);
        BlockingDto blockingDto = new BlockingDto(LocalDate.now(),
                LocalDate.of(2050,1,1),1,1,"test");
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/block")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(blockingDto)))
                .andReturn();
        assertEquals(400,mvcResult.getResponse().getStatus());
        Response actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Response.class);
        ResponseAssertion.assertThat(actualResponse).isMessageContain("endDate Should Not Be Future");
        ResponseAssertion.assertThat(actualResponse).isIdNullOrEmpty();
        ResponseAssertion.assertThat(actualResponse).isHrefNullOrEmpty();
    }

    @Test
    void blockingProperty_StartDateAfterEndDate() throws Exception {
        when(entityExistsValidator.isValid(any(),any())).thenReturn(true);
        BlockingDto blockingDto = new BlockingDto(LocalDate.of(2050,1,2),
                LocalDate.of(2050,1,1),1,1,"test");
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/block")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(blockingDto)))
                .andReturn();
        assertEquals(400,mvcResult.getResponse().getStatus());
        Response actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Response.class);
        ResponseAssertion.assertThat(actualResponse).isMessageContain("StartDate Should Be Before Or Equal To EndDate");
        ResponseAssertion.assertThat(actualResponse).isIdNullOrEmpty();
        ResponseAssertion.assertThat(actualResponse).isHrefNullOrEmpty();
    }

    @Test
    void blockingProperty_MessageLengthInvalid() throws Exception {
        when(entityExistsValidator.isValid(any(),any())).thenReturn(true);
        BlockingDto blockingDto = new BlockingDto(LocalDate.now(),
                LocalDate.now(),1,1,"test111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111");
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/block")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(blockingDto)))
                .andReturn();
        assertEquals(400,mvcResult.getResponse().getStatus());
        Response actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Response.class);
        ResponseAssertion.assertThat(actualResponse).isMessageContain("Message Length < 500");
        ResponseAssertion.assertThat(actualResponse).isIdNullOrEmpty();
        ResponseAssertion.assertThat(actualResponse).isHrefNullOrEmpty();
    }

    @Test
    void blockingProperty_PropertyNotExist() throws Exception {
        when(entityExistsValidator.isValid(any(Property.class),any())).thenReturn(false);
        BlockingDto blockingDto = new BlockingDto(LocalDate.now(),
                LocalDate.now(),1,1,"test");
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/block")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(blockingDto)))
                .andReturn();
        assertEquals(400,mvcResult.getResponse().getStatus());
        Response actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Response.class);
        ResponseAssertion.assertThat(actualResponse).isMessageContain("Property Does Not Exist");
        ResponseAssertion.assertThat(actualResponse).isIdNullOrEmpty();
        ResponseAssertion.assertThat(actualResponse).isHrefNullOrEmpty();
    }

    @Test
    void blockingProperty_ReasonNotExist() throws Exception {
        when(entityExistsValidator.isValid(any(BlockReason.class),any())).thenReturn(false);
        BlockingDto blockingDto = new BlockingDto(LocalDate.now(),
                LocalDate.now(),1,1,"test");
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/block")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(blockingDto)))
                .andReturn();
        assertEquals(400,mvcResult.getResponse().getStatus());
        Response actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Response.class);
        ResponseAssertion.assertThat(actualResponse).isMessageContain("Reason Does Not Exist");
        ResponseAssertion.assertThat(actualResponse).isIdNullOrEmpty();
        ResponseAssertion.assertThat(actualResponse).isHrefNullOrEmpty();
    }



    

    @Test
    void updateBlockedById_Regular() throws Exception {
        when(blockService.updateBlockedById(anyInt(),any(BlockingDto.class))).thenReturn(Response.builder()
                .message("Request Processed Successfully.")
                .href("/api/v1/block/10")
                .id("10").build());
        ArgumentCaptor<BlockingDto> blockingDtoArgumentCaptor = ArgumentCaptor.forClass(BlockingDto.class);
        when(entityExistsValidator.isValid(any(),any())).thenReturn(true);
        BlockingDto blockingDto = new BlockingDto(LocalDate.of(2023,1,1),
                LocalDate.now(),1,1,"test");
        MvcResult mvcResult = mockMvc.perform(put("/api/v1/block/{block-id}",10)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(blockingDto)))
                .andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
        Response actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Response.class);
        verify(blockService,times(1)).blockingProperty(blockingDtoArgumentCaptor.capture());
        BlockingDtoAssertion.assertThat(blockingDtoArgumentCaptor.getValue()).isEquivalent(blockingDto);
        ResponseAssertion.assertThat(actualResponse).hasMessage("Request Processed Successfully.");
        ResponseAssertion.assertThat(actualResponse).isHrefContain("api/v1/block/10");
        ResponseAssertion.assertThat(actualResponse).isIdEqual("10");
    }
    @Test
    void updateBlockedById_startDateFuture() throws Exception {
        when(entityExistsValidator.isValid(any(),any())).thenReturn(true);
        BlockingDto blockingDto = new BlockingDto(LocalDate.of(2050,1,1),
                LocalDate.now(),1,1,"test");
        MvcResult mvcResult = mockMvc.perform(put("/api/v1/block/{block-id}",10)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(blockingDto)))
                .andReturn();
        assertEquals(400,mvcResult.getResponse().getStatus());
        Response actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Response.class);
        ResponseAssertion.assertThat(actualResponse).isMessageContain("startDate Should Not Be Future");
        ResponseAssertion.assertThat(actualResponse).isIdNullOrEmpty();
        ResponseAssertion.assertThat(actualResponse).isHrefNullOrEmpty();
    }

    @Test
    void updateBlockedById_endDateFuture() throws Exception {
        when(entityExistsValidator.isValid(any(),any())).thenReturn(true);
        BlockingDto blockingDto = new BlockingDto(LocalDate.now(),
                LocalDate.of(2050,1,1),1,1,"test");
        MvcResult mvcResult = mockMvc.perform(put("/api/v1/block/{block-id}",10)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(blockingDto)))
                .andReturn();
        assertEquals(400,mvcResult.getResponse().getStatus());
        Response actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Response.class);
        ResponseAssertion.assertThat(actualResponse).isMessageContain("endDate Should Not Be Future");
        ResponseAssertion.assertThat(actualResponse).isIdNullOrEmpty();
        ResponseAssertion.assertThat(actualResponse).isHrefNullOrEmpty();
    }

    @Test
    void updateBlockedById_StartDateAfterEndDate() throws Exception {
        when(entityExistsValidator.isValid(any(),any())).thenReturn(true);
        BlockingDto blockingDto = new BlockingDto(LocalDate.of(2050,1,2),
                LocalDate.of(2050,1,1),1,1,"test");
        MvcResult mvcResult = mockMvc.perform(put("/api/v1/block/{block-id}",10)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(blockingDto)))
                .andReturn();
        assertEquals(400,mvcResult.getResponse().getStatus());
        Response actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Response.class);
        ResponseAssertion.assertThat(actualResponse).isMessageContain("StartDate Should Be Before Or Equal To EndDate");
        ResponseAssertion.assertThat(actualResponse).isIdNullOrEmpty();
        ResponseAssertion.assertThat(actualResponse).isHrefNullOrEmpty();
    }

    @Test
    void updateBlockedById_MessageLengthInvalid() throws Exception {
        when(entityExistsValidator.isValid(any(),any())).thenReturn(true);
        BlockingDto blockingDto = new BlockingDto(LocalDate.now(),
                LocalDate.now(),1,1,"test111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111");
        MvcResult mvcResult = mockMvc.perform(put("/api/v1/block/{block-id}",10)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(blockingDto)))
                .andReturn();
        assertEquals(400,mvcResult.getResponse().getStatus());
        Response actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Response.class);
        ResponseAssertion.assertThat(actualResponse).isMessageContain("Message Length < 500");
        ResponseAssertion.assertThat(actualResponse).isIdNullOrEmpty();
        ResponseAssertion.assertThat(actualResponse).isHrefNullOrEmpty();
    }

    @Test
    void updateBlockedById_PropertyNotExist() throws Exception {
        when(entityExistsValidator.isValid(any(Property.class),any())).thenReturn(false);
        BlockingDto blockingDto = new BlockingDto(LocalDate.now(),
                LocalDate.now(),1,1,"test");
        MvcResult mvcResult = mockMvc.perform(put("/api/v1/block/{block-id}",10)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(blockingDto)))
                .andReturn();
        assertEquals(400,mvcResult.getResponse().getStatus());
        Response actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Response.class);
        ResponseAssertion.assertThat(actualResponse).isMessageContain("Property Does Not Exist");
        ResponseAssertion.assertThat(actualResponse).isIdNullOrEmpty();
        ResponseAssertion.assertThat(actualResponse).isHrefNullOrEmpty();
    }
    @Test
    void updateBlockedById_ReasonNotExist() throws Exception {
        when(entityExistsValidator.isValid(any(BlockReason.class),any())).thenReturn(false);
        BlockingDto blockingDto = new BlockingDto(LocalDate.now(),
                LocalDate.now(),1,1,"test");
        MvcResult mvcResult = mockMvc.perform(put("/api/v1/block/{block-id}",10)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(blockingDto)))
                .andReturn();
        assertEquals(400,mvcResult.getResponse().getStatus());
        Response actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Response.class);
        ResponseAssertion.assertThat(actualResponse).isMessageContain("Reason Does Not Exist");
        ResponseAssertion.assertThat(actualResponse).isIdNullOrEmpty();
        ResponseAssertion.assertThat(actualResponse).isHrefNullOrEmpty();
    }

    @Test
    void getBlockedById_Regular() throws Exception {
        Property property = HotelProperty.builder().id(100).roomCount(2).build();
        BlockedGetDto blockedGetDto = new BlockedGetDto(LocalDate.now(),LocalDate.now(),property,"tess","reason");
        when(blockService.getBlockedById(anyInt())).thenReturn(blockedGetDto);
        ArgumentCaptor<Integer> idArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/block/{block-id}",10)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
        BlockedGetDto actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), BlockedGetDto.class);
        verify(blockService,times(1)).getBlockedById(idArgumentCaptor.capture());
        assertEquals(idArgumentCaptor.getValue(),10);
        BlockedGetDtoAssertion.assertThat(actualResponse).isEquivalent(blockedGetDto);
    }

    @Test
    void deleteBlockedById_Regular() throws Exception {
        when(blockService.deleteBlockedById(anyInt())).thenReturn(Response.builder()
                .message("Request Processed Successfully.").build());
        ArgumentCaptor<Integer> idArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        when(entityExistsValidator.isValid(any(),any())).thenReturn(true);
        BlockingDto blockingDto = new BlockingDto(LocalDate.of(2023,1,1),
                LocalDate.now(),1,1,"test");
        MvcResult mvcResult = mockMvc.perform(delete("/api/v1/block/{block-id}",10)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
        Response actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Response.class);
        verify(blockService,times(1)).deleteBlockedById(idArgumentCaptor.capture());
        assertEquals(idArgumentCaptor.getValue(),10);
        ResponseAssertion.assertThat(actualResponse).hasMessage("Request Processed Successfully.");
        ResponseAssertion.assertThat(actualResponse).isHrefNullOrEmpty();
        ResponseAssertion.assertThat(actualResponse).isIdNullOrEmpty();
    }

    @Test
    void deleteBlockedById_InvalidId() throws Exception {
        ArgumentCaptor<Integer> idArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        when(entityExistsValidator.isValid(any(),any())).thenReturn(false);
        BlockingDto blockingDto = new BlockingDto(LocalDate.of(2023,1,1),
                LocalDate.now(),1,1,"test");
        MvcResult mvcResult = mockMvc.perform(delete("/api/v1/block/{block-id}",10)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
        Response actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Response.class);
        verify(blockService,times(1)).deleteBlockedById(idArgumentCaptor.capture());
        assertEquals(idArgumentCaptor.getValue(),10);
        ResponseAssertion.assertThat(actualResponse).hasMessage("Block Does Not Exist.");
        ResponseAssertion.assertThat(actualResponse).isHrefNullOrEmpty();
        ResponseAssertion.assertThat(actualResponse).isIdNullOrEmpty();
    }
}