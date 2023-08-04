package prgrms.board.user.exception;

import lombok.Getter;
import prgrms.board.global.exception.ErrorCode;

@Getter
public class UserNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String className;

    public UserNotFoundException(ErrorCode errorCode, String className) {
        this.errorCode = errorCode;
        this.className = className;
    }
}
