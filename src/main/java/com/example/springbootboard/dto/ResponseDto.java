package com.example.springbootboard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseDto<T> {

    private HttpStatus status;
    private T data;
    private LocalDateTime serverDateTime;

    @Builder
    public ResponseDto(HttpStatus status, T data) {
        this.status = status;
        this.data = data;
        this.serverDateTime = LocalDateTime.now();
    }
}
