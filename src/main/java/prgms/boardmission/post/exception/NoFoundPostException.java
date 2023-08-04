package prgms.boardmission.post.exception;

public class NoFoundPostException extends RuntimeException {

    public NoFoundPostException() {
        super();
    }

    public NoFoundPostException(String message) {
        super(message);
    }
}
