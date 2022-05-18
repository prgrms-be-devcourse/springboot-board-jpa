package prgrms.project.post.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static java.time.LocalDateTime.now;

@Getter
public class DefaultApiResponse<T> {

    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime serverDatetime;

    private final T data;

    public DefaultApiResponse(T data) {
        this.serverDatetime = now();
        this.data = data;
    }

    public static <T> DefaultApiResponse<T> ok(T data) {
        return new DefaultApiResponse<>(data);
    }

    public static DefaultApiResponse<String> fail(ErrorType errorType) {
        return new DefaultApiResponse<>(errorType.getErrorMessage());
    }
}
