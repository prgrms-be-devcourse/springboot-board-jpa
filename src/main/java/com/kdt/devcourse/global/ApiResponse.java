package com.kdt.devcourse.global;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"message", "payload"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private static final String SUCCESS_MESSAGE = "요청을 완료하였습니다.";

    private final String message;
    private final T payload;

    public ApiResponse(T payload) {
        this.message = SUCCESS_MESSAGE;
        this.payload = payload;
    }

    public ApiResponse() {
        this.message = SUCCESS_MESSAGE;
        this.payload = null;
    }

    public String getMessage() {
        return message;
    }

    public T getPayload() {
        return payload;
    }
}
