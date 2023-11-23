package jehs.springbootboardjpa.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorMessage {
    NOT_FOUND("존재하지 않는 회원입니다.", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus httpStatus;
}
