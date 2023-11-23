package jehs.springbootboardjpa.config;

import jehs.springbootboardjpa.exception.PostException;
import jehs.springbootboardjpa.exception.UserException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PostException.class)
    public ResponseEntity<ExceptionMessage> handlePostException(PostException e){
        ExceptionMessage exceptionMessage = new ExceptionMessage(e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<>(exceptionMessage, e.getHttpStatus());
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ExceptionMessage> handleUserException(UserException e){
        ExceptionMessage exceptionMessage = new ExceptionMessage(e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<>(exceptionMessage, e.getHttpStatus());
    }
}
