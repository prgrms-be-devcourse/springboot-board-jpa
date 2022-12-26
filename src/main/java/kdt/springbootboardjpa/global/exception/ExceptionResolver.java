package kdt.springbootboardjpa.global.exception;

import jakarta.servlet.http.HttpServletRequest;
import kdt.springbootboardjpa.global.dto.ApiError;
import kdt.springbootboardjpa.global.dto.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionResolver {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({IllegalArgumentException.class})
    public BaseResponse handleForbiddenException(HttpServletRequest request, RuntimeException e) {
        ApiError apiError = new ApiError(e.getMessage(), request.getRequestURI());
        return BaseResponse.error(HttpStatus.NOT_FOUND, apiError);
    }
}
