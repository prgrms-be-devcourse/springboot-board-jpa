package com.programmers.springbootboardjpa.global.error.exception;

import com.programmers.springbootboardjpa.global.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvalidEntityValueException extends IllegalArgumentException {

    private final ErrorCode errorCode;
}
