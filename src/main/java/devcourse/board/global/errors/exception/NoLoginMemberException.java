package devcourse.board.global.errors.exception;

public class NoLoginMemberException extends IllegalStateException {

    public NoLoginMemberException(String message) {
        super(message);
    }
}
