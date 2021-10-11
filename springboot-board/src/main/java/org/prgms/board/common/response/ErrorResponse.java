package org.prgms.board.common.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {
    private int status;
    private String error;
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime serverDatetime;

    public ErrorResponse() {
    }

    @Builder
    public ErrorResponse(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.serverDatetime = LocalDateTime.now();
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(HttpStatus status, String message) {
        return ResponseEntity.status(status)
            .body(ErrorResponse.builder()
                .status(status.value())
                .error(status.name())
                .message(message)
                .build());
    }
}
