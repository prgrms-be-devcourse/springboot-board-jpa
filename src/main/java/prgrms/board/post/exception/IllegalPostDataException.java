package prgrms.board.post.exception;

import lombok.Getter;
import prgrms.board.global.exception.ErrorCode;

@Getter
public class IllegalPostDataException extends RuntimeException {

    private final ErrorCode errorCode;

    public IllegalPostDataException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
