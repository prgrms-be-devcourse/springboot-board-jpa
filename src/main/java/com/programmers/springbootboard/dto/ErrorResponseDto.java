package com.programmers.springbootboard.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDto {

    private String errorMessage;

}
