package prgrms.project.post.controller.v1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import prgrms.project.post.controller.response.ErrorResponse;

import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.*;
import static prgrms.project.post.controller.response.ErrorType.*;

@Slf4j
@RestControllerAdvice(basePackages = "prgrms.project.post.controller")
public class DefaultRestAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> catchNotFound(RuntimeException e) {
        log.error("Got unknown server error: {}", e.getMessage());

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ErrorResponse.of(SERVER_ERROR));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> catchBadRequest(IllegalArgumentException e) {
        log.error("Got bad request: {}", e.getMessage());

        return ResponseEntity.status(BAD_REQUEST).body(ErrorResponse.of(INVALID_VALUE_TYPE));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> catchNotFound(NoSuchElementException e) {
        log.error("Got not found: {}", e.getMessage());

        return ResponseEntity.status(NOT_FOUND).body(ErrorResponse.of(ENTITY_NOT_FOUND));
    }
}
