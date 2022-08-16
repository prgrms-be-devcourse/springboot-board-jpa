package com.prgrms.springbootboardjpa.exception.exceptions;

import com.prgrms.springbootboardjpa.exception.response.ErrorResponse;
import lombok.Builder;
import org.springframework.http.HttpStatus;

public enum CustomExceptionCode {
    DUPLICATE_EXCEPTION(HttpStatus.CONFLICT, "중복되는 부분이 존재합니다."),
    NO_SUCH_RESOURCE_EXCEPTION(HttpStatus.NOT_FOUND, "해당하는 리소스가 존재하지 않습니다."),
    WRONG_PASSWORD_EXCEPTION(HttpStatus.NOT_FOUND, "패스워드가 올바르지 않습니다.")
    ;

    private final HttpStatus status;
    private String errorMessage;

    CustomExceptionCode(HttpStatus status, String errorMessage){
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }
}
