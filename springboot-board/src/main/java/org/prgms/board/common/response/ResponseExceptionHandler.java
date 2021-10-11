package org.prgms.board.common.response;

import org.prgms.board.common.exception.NotFoundException;
import org.prgms.board.common.exception.NotMatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ErrorResponse> notFoundException(Exception ex) {
        return ErrorResponse.toResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({NotMatchException.class})
    public ResponseEntity<ErrorResponse> notMatchException(HttpServletRequest request, Exception ex) {
        return ErrorResponse.toResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}
