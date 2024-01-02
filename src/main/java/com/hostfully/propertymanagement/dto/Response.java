package com.hostfully.propertymanagement.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    String message;
    String id;
    String href;

    public Response(String message) {
        this.message = message;
    }
}
