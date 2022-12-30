package com.prgrms.exception;

import static com.prgrms.exception.ErrorCode.INTERNAL_SERVER_ERROR;
import static com.prgrms.exception.ErrorCode.INVALID_PARAMETER;

import com.prgrms.exception.customException.CustomException;
import com.prgrms.exception.customException.EmailDuplicateException;
import com.prgrms.exception.customException.PostNotFoundException;
import com.prgrms.exception.customException.UserNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;
import java.util.Iterator;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(EmailDuplicateException.class)
    public ResponseEntity<ErrorResponse> handleException(EmailDuplicateException e) {
        ErrorResponse response = getErrorResponse(e);
        return ResponseEntity.status(response.httpStatus()).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleException(AccessDeniedException e) {
        ErrorResponse response = new ErrorResponse(HttpStatus.UNAUTHORIZED, e.getMessage());
        return ResponseEntity.status(response.httpStatus()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse response = ErrorResponse.of(INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(response.httpStatus()).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handelException(Exception e){
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(response.httpStatus()).body(response);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleException(
        MissingServletRequestParameterException e) {
        ErrorResponse response = ErrorResponse.of(INVALID_PARAMETER);
        return ResponseEntity.status(response.httpStatus()).body(response);
    }

    @ExceptionHandler(PostNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleException(PostNotFoundException e) {
        ErrorResponse response = getErrorResponse(e);
        return ResponseEntity.status(response.httpStatus()).body(response);
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleException(UserNotFoundException e) {
        ErrorResponse response = getErrorResponse(e);
        return ResponseEntity.status(response.httpStatus()).body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ErrorResponse> handleException(ConstraintViolationException e) {
        String resultMessage = getResultMessage(e.getConstraintViolations().iterator());
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, resultMessage);
        return ResponseEntity.badRequest().body(response);
    }

    protected String getResultMessage(final Iterator<ConstraintViolation<?>> violationIterator) {
        final StringBuilder resultMessageBuilder = new StringBuilder();
        while (violationIterator.hasNext()) {
            final ConstraintViolation<?> constraintViolation = violationIterator.next();
            resultMessageBuilder
                .append("'")
                .append(getPropertyName(constraintViolation.getPropertyPath().toString()))
                .append("' is '")
                .append(constraintViolation.getInvalidValue())
                .append("'. ")
                .append(constraintViolation.getMessage());

            if (violationIterator.hasNext()) {
                resultMessageBuilder.append(", ");
            }
        }

        return resultMessageBuilder.toString();
    }

    protected String getPropertyName(String propertyPath) {
        return propertyPath.substring(propertyPath.lastIndexOf('.') + 1);
    }

    private ErrorResponse getErrorResponse(CustomException e) {

        if( Objects.nonNull(e.getMessage())) {
            return new ErrorResponse(e.getErrorCode().getStatus(),
                e.getMessage());
        }
        return new ErrorResponse(e.getErrorCode().getStatus(),
            e.getErrorCode().getMessage());
    }


}
