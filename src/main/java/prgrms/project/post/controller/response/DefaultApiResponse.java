package prgrms.project.post.controller.response;

public record DefaultApiResponse<T>(T data) {

    public static <T> DefaultApiResponse<T> of(T data) {
        return new DefaultApiResponse<>(data);
    }
}
