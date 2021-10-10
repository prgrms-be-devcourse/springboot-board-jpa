package org.prgms.board.common;

import org.prgms.board.exception.NotFoundException;
import org.prgms.board.exception.NotMatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> notFoundException(HttpServletRequest request, Exception ex) {
        return ResponseHandler.generateResponse(
                ex.getMessage(), HttpStatus.NOT_FOUND, null);
    }

    @ExceptionHandler({NotMatchException.class})
    public ResponseEntity<Object> notMatchException(HttpServletRequest request, Exception ex) {
        return ResponseHandler.generateResponse(
                ex.getMessage(), HttpStatus.BAD_REQUEST, null);
    }
}
