package prgrms.project.post.controller.response;

public record IdResponse(Long id) {
    public static IdResponse of(Long id) {
        return new IdResponse(id);
    }
}
