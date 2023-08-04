package prgrms.board.post.exception;

import lombok.Getter;
import prgrms.board.global.exception.ErrorCode;

@Getter
public class PostNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String className;

    public PostNotFoundException(ErrorCode errorCode, String className) {
        this.errorCode = errorCode;
        this.className = className;
    }
}
