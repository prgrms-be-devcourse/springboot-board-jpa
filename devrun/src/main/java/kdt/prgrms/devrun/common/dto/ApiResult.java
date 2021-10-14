package kdt.prgrms.devrun.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiResult<T> {

    private boolean success;

    private T payload;

    private LocalDateTime serverDatetime;

    private ApiError error;

    public ApiResult(boolean success, T payload, ApiError error) {
        this.success = success;
        this.payload = payload;
        this.serverDatetime = LocalDateTime.now();
        this.error = error;
    }

    public static <T>ApiResult<T> ok(T response) {
        return new ApiResult<>(true, response, null);
    }

    public static ApiResult<?> error(String code, Object message) {
        return new ApiResult<>(false, null, new ApiError(code, message));
    }

}
