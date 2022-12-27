package devcourse.board.domain.post.model;

public class PostRequest {

    private PostRequest() {
    }

    public static Post toEntity(CreationDto creationDto) {
        return new Post(creationDto.getTitle(), creationDto.getContent());
    }

    public static class CreationDto {
        private Long memberId;
        private String title;
        private String content;

        private CreationDto() {
        }

        public CreationDto(Long memberId, String title, String content) {
            this.memberId = memberId;
            this.title = title;
            this.content = content;
        }

        public Long getMemberId() {
            return memberId;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }
    }

    public static class UpdateDto {
        private String title;
        private String content;

        private UpdateDto() {
        }

        public UpdateDto(String title, String content) {
            this.title = title;
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }
    }
}
