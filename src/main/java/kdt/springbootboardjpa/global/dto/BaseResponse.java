package kdt.springbootboardjpa.global.dto;

public record BaseResponse<T>(T response, ApiError error) {

    public static <T> BaseResponse<T> success(T response) {
        return new BaseResponse<>(response, null);
    }

    public static <T> BaseResponse<T> error(ApiError error) {
        return new BaseResponse<>(null, error);
    }
}

