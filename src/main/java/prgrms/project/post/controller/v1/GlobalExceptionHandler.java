package prgrms.project.post.controller.v1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeType;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import prgrms.project.post.controller.response.ErrorCode;
import prgrms.project.post.controller.response.ErrorResponse;
import prgrms.project.post.util.exception.EntityNotFoundException;

import static java.text.MessageFormat.format;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static org.springframework.http.HttpStatus.*;
import static prgrms.project.post.controller.response.ErrorCode.*;

@Slf4j
@RestControllerAdvice(basePackages = "prgrms.project.post.controller")
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private String errorMessage;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var fieldError = ex.getFieldError();
        errorMessage = format("field: {0}, input: {1}", requireNonNull(fieldError).getField(), fieldError.getRejectedValue());
        log.warn("Got MethodArgumentNotValidException: {}", errorMessage, ex);

        return returnResponse(BAD_REQUEST, INVALID_INPUT_VALUE);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var supported = ex.getSupportedMediaTypes().stream().map(MimeType::toString).collect(joining());
        errorMessage = format("{0} is not supported, supported: {1}", ex.getContentType(), supported);
        log.warn("Got HttpMediaTypeNotSupportedException: {}", errorMessage, ex);

        return returnResponse(UNSUPPORTED_MEDIA_TYPE, NOT_SUPPORTED_MEDIA_TYPE);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        errorMessage = format("{0} is different from the requested type({1})", ex.getValue(), requireNonNull(ex.getRequiredType()).getSimpleName());
        log.warn("Got TypeMismatchException: {}", errorMessage, ex);

        return returnResponse(BAD_REQUEST, TYPE_MISMATCH);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Got unknown server error: {}", ex.getLocalizedMessage(), ex);

        return returnResponse(INTERNAL_SERVER_ERROR, SERVER_ERROR);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleNoSuchElementException(EntityNotFoundException ex) {
        log.warn("Got EntityNotFoundException: {}", ex.getMessage(), ex);

        return returnResponse(NOT_FOUND, ENTITY_NOT_FOUND);
    }

    private ResponseEntity<Object> returnResponse(HttpStatus httpStatus, ErrorCode errorCode) {
        return ResponseEntity.status(httpStatus).body(ErrorResponse.of(errorCode));
    }
}
