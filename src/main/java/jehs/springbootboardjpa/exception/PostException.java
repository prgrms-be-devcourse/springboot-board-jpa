package jehs.springbootboardjpa.exception;

import org.springframework.http.HttpStatus;

public class PostException extends RuntimeException{

    private final HttpStatus httpStatus;

    public PostException(PostErrorMessage postErrorMessage) {
        super(postErrorMessage.getMessage());
        this.httpStatus = postErrorMessage.getHttpStatus();
    }

    public HttpStatus getHttpStatus(){
        return httpStatus;
    }
}
