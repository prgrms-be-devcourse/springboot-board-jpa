package com.example.yiseul.global;

import com.example.yiseul.global.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private final String error;
    private final String code;
    private final String message;

    public static ErrorResponse of(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .error(errorCode.name())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
    }
}
