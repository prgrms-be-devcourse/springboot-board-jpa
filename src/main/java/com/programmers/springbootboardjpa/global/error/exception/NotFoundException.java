package com.programmers.springbootboardjpa.global.error.exception;

import com.programmers.springbootboardjpa.global.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotFoundException extends RuntimeException {

    private final ErrorCode errorCode;
}
