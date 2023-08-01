package com.programmers.springbootboardjpa.global.exception;

import com.programmers.springbootboardjpa.global.ApiResponse;
import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<ErrorResponse> handlerValidationException(MethodArgumentNotValidException methodArgumentNotValidException) {
        ErrorResponse errorResponse = createValidationErrorResponse(methodArgumentNotValidException);
        
        return ApiResponse.fail(errorResponse);
    }

    @ExceptionHandler(UserException.class)
    public ApiResponse<ErrorResponse> handlerUserException(UserException userException) {
        ErrorResponse errorResponse = createErrorResponse(userException);

        return ApiResponse.fail(errorResponse);
    }

    @ExceptionHandler(PostException.class)
    public ApiResponse<ErrorResponse> handlerPostException(PostException postException) {
        ErrorResponse errorResponse = createErrorResponse(postException);

        return ApiResponse.fail(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<ErrorResponse> handlerException(Exception exception) {
        ErrorResponse errorResponse = createTopLevelErrorResponse(exception);

        return ApiResponse.fail(errorResponse);
    }

    private ErrorResponse createErrorResponse(BusinessException businessException) {
        if (businessException.getStatus() == null || businessException.getMessage() == null) {
            throw new NullPointerException("예외 설정이 잘못되었습니다.");
        }

        return ErrorResponse.builder()
                .status(businessException.getStatus())
                .statusCode(businessException.getStatus().value())
                .errorMessage(businessException.getMessage())
                .build();
    }

    private ErrorResponse createValidationErrorResponse(MethodArgumentNotValidException exception) {
        String errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(" , "));

        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .statusCode(exception.getStatusCode().value())
                .errorMessage(errors)
                .build();
    }


    private ErrorResponse createTopLevelErrorResponse(Exception exception) {
        return ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .statusCode(500)
                .errorMessage("서버에서 알 수 없는 오류가 발생했습니다.")
                .build();
    }

}
