package prgrms.board.global.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

public enum ErrorCode {
    INVALID_USER_AGE(BAD_REQUEST, "나이는 7세이상 120세 이하의 값만 가능합니다."),
    INVALID_USER_NAME(BAD_REQUEST, "이름은 50글자이하 영어 혹은 한글만 가능합니다."),

    EMPTY_POST_TITLE(BAD_REQUEST, "게시글의 제목은 50글자이하로 비어있으면 안됩니다."),
    EMPTY_POST_CONTENT(BAD_REQUEST, "게시글의 본문은 비어있으면 안됩니다."),
    INVALID_CREATED_BY_VALUE(BAD_REQUEST, "작성자의 이름은 50글자이하로 비어있으면 안됩니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
