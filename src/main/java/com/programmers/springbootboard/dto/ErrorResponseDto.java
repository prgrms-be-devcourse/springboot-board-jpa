package com.programmers.springbootboard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDto {

    @JsonProperty(value = "error_message")
    private String errorMessage;

}
