package me.kimihiqq.springbootboardjpa.post.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import me.kimihiqq.springbootboardjpa.post.domain.Post;
import me.kimihiqq.springbootboardjpa.user.domain.User;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class PostCreateRequest {

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;
    private Long userId;

    public Post toEntity(User user) {
        System.out.println("toentity" );
        return Post.builder()
                .title(title)
                .content(content)
                .user(user)
                .build();
    }
}

