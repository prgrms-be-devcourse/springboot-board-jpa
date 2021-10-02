package kr.ac.hs.oing.exception;

public class InvalidArgumentException extends IllegalArgumentException {
    public InvalidArgumentException(ErrorMessage message) {
        super(message.message());
    }
}
