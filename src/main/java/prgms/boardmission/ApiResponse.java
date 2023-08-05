package prgms.boardmission;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class ApiResponse<T> {
    private int statusCode;
    private T data;

    public ApiResponse(int statusCode, T data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(200, data);
    }

    public static <T> ApiResponse<T> fail(int statusCode, T data) {
        return new ApiResponse<>(statusCode, data);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public T getData() {
        return data;
    }
}
