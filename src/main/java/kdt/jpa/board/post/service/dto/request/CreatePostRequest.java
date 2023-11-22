package kdt.jpa.board.post.service.dto.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record CreatePostRequest(
    String title,
    String content,
    long userId
) {

}
