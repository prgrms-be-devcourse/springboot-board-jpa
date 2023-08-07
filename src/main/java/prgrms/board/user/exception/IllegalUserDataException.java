package prgrms.board.user.exception;

import lombok.Getter;
import prgrms.board.global.exception.ErrorCode;

@Getter
public class IllegalUserDataException extends RuntimeException {

    private final ErrorCode errorCode;

    public IllegalUserDataException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
