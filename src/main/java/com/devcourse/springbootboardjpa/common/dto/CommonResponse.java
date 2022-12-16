package com.devcourse.springbootboardjpa.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommonResponse<T> extends ResponseDTO {

    private final T data;

    @Builder
    public CommonResponse(String message, T data) {
        super(200, message);
        this.data = data;
    }

}
