package com.prgrms.board.post.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ApiResponse<T> {
    private HttpStatus httpStatus;

    private T data;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime serverDatetime;

    public ApiResponse(HttpStatus httpStatus, T data) {
        this.httpStatus = httpStatus;
        this.data = data;
        this.serverDatetime = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<T>(HttpStatus.OK, data);
    }
}
