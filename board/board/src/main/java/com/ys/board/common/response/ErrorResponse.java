package com.ys.board.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private LocalDateTime timeStamp;

    private String message;

    private String requestUrl;

    @Builder
    public ErrorResponse(LocalDateTime timeStamp, String message,
        String requestUrl) {
        this.timeStamp = timeStamp;
        this.message = message;
        this.requestUrl = requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

}
