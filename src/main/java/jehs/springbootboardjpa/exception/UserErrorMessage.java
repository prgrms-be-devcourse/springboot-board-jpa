package jehs.springbootboardjpa.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorMessage {
    NOT_FOUND("존재하지 않는 회원입니다.", HttpStatus.NOT_FOUND),
    INVALID_AGE("나이는 0보다 작을 수 없습니다.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;
}
