package dev.jpaboard.common.advice;

import dev.jpaboard.common.exception.AuthorizedException;
import dev.jpaboard.common.exception.ForbiddenException;
import dev.jpaboard.common.exception.NotFoundException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestControllerAdvice
public class ControllerExceptionAdvice {

    private static final String SERVER_ERROR_MESSAGE = "알 수 없는 문제가 발생했습니다.";

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler({AuthorizedException.class, ServletRequestBindingException.class})
    @ResponseStatus(UNAUTHORIZED)
    public ErrorResponse handleAuthorizedException(Exception e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(FORBIDDEN)
    public ErrorResponse handleForbiddenException(ForbiddenException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handleRuntimeException() {
        return new ErrorResponse(SERVER_ERROR_MESSAGE);
    }

}
