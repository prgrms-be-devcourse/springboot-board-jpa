package org.prgms.board.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse<T> {
    private int status;
    private T data;

    public ApiResponse() {
    }

    public ApiResponse(int status) {
        this.status = status;
    }

    public ApiResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    public static <T> ApiResponse<T> toResponse(T data) {
        return new ApiResponse<T>(HttpStatus.OK.value(), data);
    }

    public static <T> ApiResponse<T> ok() {
        return new ApiResponse<T>(HttpStatus.NO_CONTENT.value());
    }
}
