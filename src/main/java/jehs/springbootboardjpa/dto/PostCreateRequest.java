package jehs.springbootboardjpa.dto;

import jehs.springbootboardjpa.entity.Post;
import jehs.springbootboardjpa.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostCreateRequest {

    private final String title;
    private final String content;
    private final Long userId;
    private final Post.PostType postType;

    public Post toEntity(User user) {
        return Post.builder()
                .title(title)
                .content(content)
                .user(user)
                .postType(postType)
                .build();
    }
}
