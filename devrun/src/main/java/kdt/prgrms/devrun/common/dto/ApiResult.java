package kdt.prgrms.devrun.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ApiResult<T> {

    private boolean success;

    private int status;

    private T payload;

    private ApiError error;

    public static <T>ApiResult<T> ok(T response) {
        return new ApiResult<>(true, HttpStatus.OK.value(), response, null);
    }

    public static ApiResult<?> error(String code, Object message) {
        return new ApiResult<>(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), null, new ApiError(code, message));
    }

}
