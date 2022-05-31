package prgrms.project.post.controller.response;

public record ErrorResponse(
        String error,
        String errorMessage
) {

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.name(), errorCode.getErrorMessage());
    }
}
