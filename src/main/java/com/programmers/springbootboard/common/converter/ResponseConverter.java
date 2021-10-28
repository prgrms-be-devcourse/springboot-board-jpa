package com.programmers.springbootboard.common.converter;

import com.programmers.springbootboard.common.dto.ResponseDto;
import com.programmers.springbootboard.common.dto.ResponseMessage;
import com.programmers.springbootboard.error.ErrorMessage;
import com.programmers.springbootboard.error.ErrorResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;

public class ResponseConverter {
    public static ResponseEntity<ResponseDto> toResponseEntity(ResponseMessage message, EntityModel model) {
        return ResponseEntity.ok(
                ResponseDto.of(message, model)
        );
    }

    public static <T> ResponseEntity<ResponseDto> toResponseEntity(ResponseMessage message, Page<T> pages, Link link) {
        return ResponseEntity.ok(
                ResponseDto.of(message, pages, link)
        );
    }

    public static ResponseEntity<ErrorResponseDto> toResponseEntity(ErrorMessage message) {
        return ResponseEntity.ok(
                ErrorResponseDto.of(message)
        );
    }
}
