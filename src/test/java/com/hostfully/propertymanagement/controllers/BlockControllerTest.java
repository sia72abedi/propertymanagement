package com.hostfully.propertymanagement.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hostfully.propertymanagement.customassertion.BlockedGetDtoAssertion;
import com.hostfully.propertymanagement.customassertion.BlockingDtoAssertion;
import com.hostfully.propertymanagement.customassertion.ResponseAssertion;
import com.hostfully.propertymanagement.dto.BlockedGetDto;
import com.hostfully.propertymanagement.dto.BlockingDto;
import com.hostfully.propertymanagement.dto.Response;
import com.hostfully.propertymanagement.entities.HotelProperty;
import com.hostfully.propertymanagement.entities.Property;
import com.hostfully.propertymanagement.services.BlockService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
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
    private EntityManager entityManager;
    @MockBean
    private TypedQuery query;

    @Test
    void blockingProperty_Regular() throws Exception {
        when(blockService.blockingProperty(any(BlockingDto.class))).thenReturn(Response.builder()
                .message("Request Processed Successfully.")
                .href("/api/v1/blocks/10")
                .id("10").build());
        ArgumentCaptor<BlockingDto> blockingDtoArgumentCaptor = ArgumentCaptor.forClass(BlockingDto.class);
        when(entityManager.createQuery(any(),any())).thenReturn(query);
        when(query.setParameter(eq("id"), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(1L);
        BlockingDto blockingDto = new BlockingDto(LocalDate.now(),
                LocalDate.now(),1,1,"test");
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/blocks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(blockingDto)))
                .andReturn();
        assertEquals(201,mvcResult.getResponse().getStatus());
        Response actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Response.class);
        verify(blockService,times(1)).blockingProperty(blockingDtoArgumentCaptor.capture());
        BlockingDtoAssertion.assertThat(blockingDtoArgumentCaptor.getValue()).isEquivalent(blockingDto);
        ResponseAssertion.assertThat(actualResponse).hasMessage("Request Processed Successfully.");
        ResponseAssertion.assertThat(actualResponse).isHrefContain("api/v1/blocks");
    }
    @Test
    void blockingProperty_startDatePast() throws Exception {
        when(entityManager.createQuery(any(),any())).thenReturn(query);
        when(query.setParameter(eq("id"), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(1L);
        BlockingDto blockingDto = new BlockingDto(LocalDate.of(2023,1,1),
                LocalDate.now(),1,1,"test");
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/blocks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(blockingDto)))
                .andReturn();
        assertEquals(400,mvcResult.getResponse().getStatus());
        Response actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Response.class);
        ResponseAssertion.assertThat(actualResponse).isMessageContain("startDate Should Not Be Past.");
        ResponseAssertion.assertThat(actualResponse).isIdNull();
        ResponseAssertion.assertThat(actualResponse).isHrefNull();
    }

    @Test
    void blockingProperty_endDatePast() throws Exception {
        when(entityManager.createQuery(any(),any())).thenReturn(query);
        when(query.setParameter(eq("id"), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(1L);
        BlockingDto blockingDto = new BlockingDto(LocalDate.now(),
                LocalDate.of(2023,1,1),1,1,"test");
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/blocks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(blockingDto)))
                .andReturn();
        assertEquals(400,mvcResult.getResponse().getStatus());
        Response actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Response.class);
        ResponseAssertion.assertThat(actualResponse).isMessageContain("endDate Should Not Be Past.");
        ResponseAssertion.assertThat(actualResponse).isIdNull();
        ResponseAssertion.assertThat(actualResponse).isHrefNull();
    }

    @Test
    void blockingProperty_StartDateAfterEndDate() throws Exception {
        when(entityManager.createQuery(any(),any())).thenReturn(query);
        when(query.setParameter(eq("id"), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(1L);
        BlockingDto blockingDto = new BlockingDto(LocalDate.of(2050,1,2),
                LocalDate.of(2050,1,1),1,1,"test");
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/blocks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(blockingDto)))
                .andReturn();
        assertEquals(400,mvcResult.getResponse().getStatus());
        Response actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Response.class);
        ResponseAssertion.assertThat(actualResponse).isMessageContain("StartDate Should Be Before Or Equal To EndDate");
        ResponseAssertion.assertThat(actualResponse).isIdNull();
        ResponseAssertion.assertThat(actualResponse).isHrefNull();
    }

    @Test
    void blockingProperty_MessageLengthInvalid() throws Exception {
        when(entityManager.createQuery(any(),any())).thenReturn(query);
        when(query.setParameter(eq("id"), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(1L);
        BlockingDto blockingDto = new BlockingDto(LocalDate.now(),
                LocalDate.now(),1,1,"test111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111");
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/blocks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(blockingDto)))
                .andReturn();
        assertEquals(400,mvcResult.getResponse().getStatus());
        Response actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Response.class);
        ResponseAssertion.assertThat(actualResponse).isMessageContain("Message Length < 500");
        ResponseAssertion.assertThat(actualResponse).isIdNull();
        ResponseAssertion.assertThat(actualResponse).isHrefNull();
    }

    @Test
    void blockingProperty_PropertyNotExist() throws Exception {
                when(entityManager.createQuery(any(),any())).thenReturn(query);
        when(query.setParameter(eq("id"), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(0L);
        BlockingDto blockingDto = new BlockingDto(LocalDate.now(),
                LocalDate.now(),1,1,"test");
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/blocks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(blockingDto)))
                .andReturn();
        assertEquals(400,mvcResult.getResponse().getStatus());
        Response actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Response.class);
        ResponseAssertion.assertThat(actualResponse).isMessageContain("Property Does Not Exist");
        ResponseAssertion.assertThat(actualResponse).isIdNull();
        ResponseAssertion.assertThat(actualResponse).isHrefNull();
    }

    @Test
    void blockingProperty_TestEntityExistValidator() throws Exception {
        when(entityManager.createQuery(any(),any())).thenReturn(query);
        when(query.setParameter(eq("id"), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(1L);
        ArgumentCaptor<String> queryCaptor = ArgumentCaptor.forClass(String.class);
        BlockingDto blockingDto = new BlockingDto(LocalDate.now(),
                LocalDate.now(),1,1,"test");
        mockMvc.perform(post("/api/v1/blocks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(blockingDto)))
                .andReturn();
        verify(entityManager,times(2)).createQuery(queryCaptor.capture(), any(Class.class));
        String regex = "^SELECT\\s+COUNT\\(\\w+\\)\\s+FROM\\s+\\w+\\s+\\w+\\s+WHERE\\s+\\w+\\.\\w+\\s*=\\s*:\\w+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(queryCaptor.getValue());
        assertTrue(matcher.find());
    }

    @Test
    void blockingProperty_ReasonNotExist() throws Exception {
                when(entityManager.createQuery(any(),any())).thenReturn(query);
        when(query.setParameter(eq("id"), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(0L);
        BlockingDto blockingDto = new BlockingDto(LocalDate.now(),
                LocalDate.now(),1,1,"test");
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/blocks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(blockingDto)))
                .andReturn();
        assertEquals(400,mvcResult.getResponse().getStatus());
        Response actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Response.class);
        ResponseAssertion.assertThat(actualResponse).isMessageContain("Reason Does Not Exist");
        ResponseAssertion.assertThat(actualResponse).isIdNull();
        ResponseAssertion.assertThat(actualResponse).isHrefNull();
    }





    @Test
    void updateBlockedById_Regular() throws Exception {
        when(blockService.updateBlockedById(anyInt(),any(BlockingDto.class))).thenReturn(Response.builder()
                .message("Request Processed Successfully.")
                .href("/api/v1/blocks/10")
                .id("10").build());
        ArgumentCaptor<BlockingDto> blockingDtoArgumentCaptor = ArgumentCaptor.forClass(BlockingDto.class);
        ArgumentCaptor<Integer> idArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        when(entityManager.createQuery(any(),any())).thenReturn(query);
        when(query.setParameter(eq("id"), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(1L);
        BlockingDto blockingDto = new BlockingDto(LocalDate.now(),
                LocalDate.now(),1,1,"test");
        MvcResult mvcResult = mockMvc.perform(put("/api/v1/blocks/{block-id}",10)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(blockingDto)))
                .andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
        Response actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Response.class);
        verify(blockService,times(1)).updateBlockedById(idArgumentCaptor.capture(),blockingDtoArgumentCaptor.capture());
        BlockingDtoAssertion.assertThat(blockingDtoArgumentCaptor.getValue()).isEquivalent(blockingDto);
        assertEquals(idArgumentCaptor.getValue(),10);
        ResponseAssertion.assertThat(actualResponse).hasMessage("Request Processed Successfully.");
        ResponseAssertion.assertThat(actualResponse).isHrefContain("api/v1/blocks/10");
        ResponseAssertion.assertThat(actualResponse).isIdEqual("10");
    }
    @Test
    void updateBlockedById_startDatePast() throws Exception {
        when(entityManager.createQuery(any(),any())).thenReturn(query);
        when(query.setParameter(eq("id"), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(1L);
        BlockingDto blockingDto = new BlockingDto(LocalDate.of(2023,1,1),
                LocalDate.now(),1,1,"test");
        MvcResult mvcResult = mockMvc.perform(put("/api/v1/blocks/{block-id}",10)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(blockingDto)))
                .andReturn();
        assertEquals(400,mvcResult.getResponse().getStatus());
        Response actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Response.class);
        ResponseAssertion.assertThat(actualResponse).isMessageContain("startDate Should Not Be Past.");
        ResponseAssertion.assertThat(actualResponse).isIdNull();
        ResponseAssertion.assertThat(actualResponse).isHrefNull();
    }

    @Test
    void updateBlockedById_endDatePast() throws Exception {
        when(entityManager.createQuery(any(),any())).thenReturn(query);
        when(query.setParameter(eq("id"), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(1L);
        BlockingDto blockingDto = new BlockingDto(LocalDate.now(),
                LocalDate.of(2023,1,1),1,1,"test");
        MvcResult mvcResult = mockMvc.perform(put("/api/v1/blocks/{block-id}",10)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(blockingDto)))
                .andReturn();
        assertEquals(400,mvcResult.getResponse().getStatus());
        Response actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Response.class);
        ResponseAssertion.assertThat(actualResponse).isMessageContain("endDate Should Not Be Past.");
        ResponseAssertion.assertThat(actualResponse).isIdNull();
        ResponseAssertion.assertThat(actualResponse).isHrefNull();
    }

    @Test
    void updateBlockedById_StartDateAfterEndDate() throws Exception {
        when(entityManager.createQuery(any(),any())).thenReturn(query);
        when(query.setParameter(eq("id"), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(1L);
        BlockingDto blockingDto = new BlockingDto(LocalDate.of(2050,1,2),
                LocalDate.of(2050,1,1),1,1,"test");
        MvcResult mvcResult = mockMvc.perform(put("/api/v1/blocks/{block-id}",10)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(blockingDto)))
                .andReturn();
        assertEquals(400,mvcResult.getResponse().getStatus());
        Response actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Response.class);
        ResponseAssertion.assertThat(actualResponse).isMessageContain("StartDate Should Be Before Or Equal To EndDate");
        ResponseAssertion.assertThat(actualResponse).isIdNull();
        ResponseAssertion.assertThat(actualResponse).isHrefNull();
    }

    @Test
    void updateBlockedById_MessageLengthInvalid() throws Exception {
        when(entityManager.createQuery(any(),any())).thenReturn(query);
        when(query.setParameter(eq("id"), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(1L);
        BlockingDto blockingDto = new BlockingDto(LocalDate.now(),
                LocalDate.now(),1,1,"test111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111");
        MvcResult mvcResult = mockMvc.perform(put("/api/v1/blocks/{block-id}",10)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(blockingDto)))
                .andReturn();
        assertEquals(400,mvcResult.getResponse().getStatus());
        Response actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Response.class);
        ResponseAssertion.assertThat(actualResponse).isMessageContain("Message Length < 500");
        ResponseAssertion.assertThat(actualResponse).isIdNull();
        ResponseAssertion.assertThat(actualResponse).isHrefNull();
    }

    @Test
    void updateBlockedById_PropertyNotExist() throws Exception {
        when(entityManager.createQuery(any(),any())).thenReturn(query);
        when(query.setParameter(eq("id"), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(0L);
        BlockingDto blockingDto = new BlockingDto(LocalDate.now(),
                LocalDate.now(),1,1,"test");
        MvcResult mvcResult = mockMvc.perform(put("/api/v1/blocks/{block-id}",10)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(blockingDto)))
                .andReturn();
        assertEquals(400,mvcResult.getResponse().getStatus());
        Response actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Response.class);
        ResponseAssertion.assertThat(actualResponse).isMessageContain("Property Does Not Exist");
        ResponseAssertion.assertThat(actualResponse).isIdNull();
        ResponseAssertion.assertThat(actualResponse).isHrefNull();
    }
    @Test
    void updateBlockedById_ReasonNotExist() throws Exception {
        when(entityManager.createQuery(any(),any())).thenReturn(query);
        when(query.setParameter(eq("id"), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(0L);
        BlockingDto blockingDto = new BlockingDto(LocalDate.now(),
                LocalDate.now(),1,1,"test");
        MvcResult mvcResult = mockMvc.perform(put("/api/v1/blocks/{block-id}",10)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(blockingDto)))
                .andReturn();
        assertEquals(400,mvcResult.getResponse().getStatus());
        Response actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Response.class);
        ResponseAssertion.assertThat(actualResponse).isMessageContain("Reason Does Not Exist");
        ResponseAssertion.assertThat(actualResponse).isIdNull();
        ResponseAssertion.assertThat(actualResponse).isHrefNull();
    }

    @Test
    void getBlockedById_Regular() throws Exception {
        Property property = HotelProperty.builder().id(100).hasBar(true).roomCount(2).build();
        BlockedGetDto blockedGetDto = new BlockedGetDto(LocalDate.now(),LocalDate.now(),property,"tess","reason");
        when(blockService.getBlockedById(anyInt())).thenReturn(blockedGetDto);
        ArgumentCaptor<Integer> idArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/blocks/{block-id}",10)
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
        MvcResult mvcResult = mockMvc.perform(delete("/api/v1/blocks/{block-id}",10)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
        Response actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Response.class);
        verify(blockService,times(1)).deleteBlockedById(idArgumentCaptor.capture());
        assertEquals(idArgumentCaptor.getValue(),10);
        ResponseAssertion.assertThat(actualResponse).hasMessage("Request Processed Successfully.");
        ResponseAssertion.assertThat(actualResponse).isHrefNull();
        ResponseAssertion.assertThat(actualResponse).isIdNull();
    }
}