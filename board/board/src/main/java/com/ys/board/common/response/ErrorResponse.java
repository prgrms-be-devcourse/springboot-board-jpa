package com.ys.board.common.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH-mm-ss", timezone = "Asia/Seoul")
    private LocalDateTime timeStamp;

    private String message;

    private String requestUrl;

    private String method;

    @Builder
    public ErrorResponse(LocalDateTime timeStamp, String message,
        String requestUrl, String method) {
        this.timeStamp = timeStamp;
        this.message = message;
        this.requestUrl = requestUrl;
        this.method = method;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
