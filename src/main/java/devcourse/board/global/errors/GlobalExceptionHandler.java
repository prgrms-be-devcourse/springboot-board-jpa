package devcourse.board.global.errors;

import devcourse.board.global.errors.exception.ForbiddenException;
import devcourse.board.global.errors.exception.NoLoginMemberException;
import devcourse.board.global.errors.model.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        log.warn("handleMethodArgumentNotValid", e);
        String errorMessage = e.getBindingResult()
                .getFieldError()
                .getDefaultMessage();
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(errorMessage));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("handleIllegalArgumentException", e);
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(IllegalStateException.class)
    private ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException e) {
        log.warn("handleIllegalStateException", e);
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(ForbiddenException.class)
    private ResponseEntity<ErrorResponse> handleForbiddenException(ForbiddenException e) {
        log.warn("handleForbiddenException", e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse("No login info exists. Please login first."));
    }

    @ExceptionHandler(NoLoginMemberException.class)
    private ResponseEntity<ErrorResponse> handleNoLoginMemberException(NoLoginMemberException e) {
        log.warn("handleNoLoginMemberException", e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse("No login info exists. Please login first."));
    }
}
