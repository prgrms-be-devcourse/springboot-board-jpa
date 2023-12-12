package jehs.springbootboardjpa.dto;

import jehs.springbootboardjpa.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final Post.PostType postType;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final UserResponse userResponse;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.postType = post.getPostType();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
        this.userResponse = new UserResponse(post.getUser());
    }
}
