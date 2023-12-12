package jehs.springbootboardjpa.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PostErrorMessage {

    NOT_FOUND("존재하지 않는 게시글입니다.", HttpStatus.NOT_FOUND),
    NOT_POST_BY_USER("작성자의 게시글이 아닙니다.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;
}
