package jehs.springbootboardjpa.exception;

import org.springframework.http.HttpStatus;

public class UserException extends RuntimeException {

    private final HttpStatus httpStatus;

    public UserException(UserErrorMessage userErrorMessage) {
        super(userErrorMessage.getMessage());
        this.httpStatus = userErrorMessage.getHttpStatus();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
