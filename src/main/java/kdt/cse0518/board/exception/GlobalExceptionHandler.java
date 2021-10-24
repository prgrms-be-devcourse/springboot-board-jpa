package kdt.cse0518.board.exception;

import kdt.cse0518.board.common.api.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> exceptionHandle(final Exception e) {
        return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ApiResponse<String> notFoundHandle(final NullPointerException e) {
        return ApiResponse.fail(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

}
