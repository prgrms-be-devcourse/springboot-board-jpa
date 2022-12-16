package com.devcourse.springbootboardjpa.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResponseDTO {

    private final Integer status;
    private final String message;

}
