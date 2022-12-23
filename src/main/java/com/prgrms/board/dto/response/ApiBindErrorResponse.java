package com.prgrms.board.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiBindErrorResponse {
    private String objectName;
    private String field;
    private String errorMessage;
    private int statusCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime serverDatetime;

    public ApiBindErrorResponse(String objectName, String field, String errorMessage, int statusCode) {
        this.objectName = objectName;
        this.field = field;
        this.errorMessage = errorMessage;
        this.statusCode = statusCode;
        this.serverDatetime = LocalDateTime.now();
    }

    public static ApiBindErrorResponse fail(String objectName, String field, String errorMessage, int statusCode) {
        return new ApiBindErrorResponse(objectName, field, errorMessage, statusCode);
    }
}

