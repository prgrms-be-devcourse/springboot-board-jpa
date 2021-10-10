package com.example.springbootboard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class ResponseError {

    private String errorMessage;
    private String detailMessage;

    @Builder
    public ResponseError(String errorMessage, String detailMessage) {
        this.errorMessage = errorMessage;
        this.detailMessage = detailMessage;
    }
}
