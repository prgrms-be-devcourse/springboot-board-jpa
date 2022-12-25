package com.devcourse.springbootboardjpa.common.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CommonResponse<T> extends ResponseDTO {

    private final T data;

    @Builder
    public CommonResponse(HttpStatus status, String message, T data) {
        super(status.value(), message);
        this.data = data;
    }

}
