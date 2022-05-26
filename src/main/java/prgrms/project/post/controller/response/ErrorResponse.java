package prgrms.project.post.controller.response;

public record ErrorResponse(
        String error,
        String errorMessage
) {

    public static ErrorResponse of(ErrorType errorType) {
        return new ErrorResponse(errorType.getError(), errorType.getErrorMessage());
    }
}
