package com.prgrms.board.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApiErrorResponse {
    private String errorMessage;
    private int statusCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime serverDatetime;

    public ApiErrorResponse(String errorMessage, int statusCode) {
        this.errorMessage = errorMessage;
        this.statusCode = statusCode;
        this.serverDatetime = LocalDateTime.now();
    }

    public static ApiErrorResponse fail(String errorMessage, int statusCode) {
        return new ApiErrorResponse(errorMessage, statusCode);
    }
}

