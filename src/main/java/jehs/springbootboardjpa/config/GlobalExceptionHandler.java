package jehs.springbootboardjpa.config;

import jehs.springbootboardjpa.exception.PostException;
import jehs.springbootboardjpa.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PostException.class)
    public ResponseEntity<ExceptionMessage> handlePostException(PostException e) {
        log.warn(e.getMessage());
        ExceptionMessage exceptionMessage = new ExceptionMessage(e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<>(exceptionMessage, e.getHttpStatus());
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ExceptionMessage> handleUserException(UserException e) {
        log.warn(e.getMessage());
        ExceptionMessage exceptionMessage = new ExceptionMessage(e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<>(exceptionMessage, e.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionMessage> handleException(Exception e) {
        log.error(e.getMessage());
        ExceptionMessage exceptionMessage = new ExceptionMessage("Internal Server Error", "서버 내부 에러가 발생했습니다.");
        return new ResponseEntity<>(exceptionMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
