package devcourse.board.errors.exception;

public class ForbiddenException extends IllegalArgumentException {

    public ForbiddenException(String message) {
        super(message);
    }
}
