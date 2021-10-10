package com.kdt.aop;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.validation.Errors;

@Getter
@JsonInclude(Include.NON_EMPTY)
public class ErrorResponse {

    private Errors errors;

    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime serverDatetime;

    public ErrorResponse(Errors errors) {
        this.errors = errors;
        this.serverDatetime = LocalDateTime.now();
    }

    public ErrorResponse(String message) {
        this.message = message;
        this.serverDatetime = LocalDateTime.now();
    }

    public static ErrorResponse of(Errors errors) {
        return new ErrorResponse(errors);
    }

    public static ErrorResponse of(String message) {
        return new ErrorResponse(message);
    }

}
