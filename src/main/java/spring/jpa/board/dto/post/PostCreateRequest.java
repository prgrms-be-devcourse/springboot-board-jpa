package spring.jpa.board.dto.post;

public record PostCreateRequest(String title, String content, Long userId) {

}
