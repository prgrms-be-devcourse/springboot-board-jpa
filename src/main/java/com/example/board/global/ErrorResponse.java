package com.example.board.global;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
    private ErrorCode status;
    private String message;

    public static ErrorResponse of(ErrorCode status, String message) {
        return ErrorResponse.builder()
                .status(status)
                .message(message)
                .build();
    }

    public static ErrorResponse of(ErrorCode status) {
        return ErrorResponse.builder()
                .status(status)
                .build();
    }
}
