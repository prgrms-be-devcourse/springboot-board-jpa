package com.example.board.global;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
    private String uniqueMessage;
    private String message;

    public static ErrorResponse of(String uniqueMessage, String message) {
        return ErrorResponse.builder()
                .uniqueMessage(uniqueMessage)
                .message(message)
                .build();
    }

    public static ErrorResponse of(String uniqueMessage) {
        return ErrorResponse.builder()
                .uniqueMessage(uniqueMessage)
                .build();
    }
}
