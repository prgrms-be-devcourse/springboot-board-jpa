package prgrms.project.post.controller.response;

public record DefaultErrorResponse(
        String error,
        String errorMessage
) {

    public static DefaultErrorResponse of(ErrorType errorType) {
        return new DefaultErrorResponse(errorType.getError(), errorType.getErrorMessage());
    }
}
