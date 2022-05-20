package com.prgrms.board.post.dto;

import com.prgrms.board.post.domain.Post;
import com.prgrms.board.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostRequest {
    private String title;
    private String content;

    public Post toPost(User user) {
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .user(user)
                .build();
    }
}
