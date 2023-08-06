package com.example.jpaboard.global;

public class ApiResponse<T> {

    private T result;
    private int resultCode;
    private String resultMsg;

    public ApiResponse(final T result, SuccessCode successCode) {
        this.result = result;
        this.resultCode = successCode.getStatus();
        this.resultMsg = successCode.getMessage();
    }

    public T getResult() {
        return result;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

}
