package com.devcourse.bbs.domain.post;

import com.devcourse.bbs.domain.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class PostDTO {
    private final long id;
    private final String title;
    private final String content;
    private final UserDTO user;

    public PostDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.user = post.getUser().toDTO();
    }
}
