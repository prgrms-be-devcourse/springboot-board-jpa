package prgms.boardmission;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiResponse<T> {
    private HttpStatus statusCode;
    private T data;

    public ApiResponse(HttpStatus statusCode, T data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(HttpStatus.OK, data);
    }

    public static <T> ApiResponse<T> fail(HttpStatus statusCode, T data) {
        return new ApiResponse<>(statusCode, data);
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public T getData() {
        return data;
    }
}
