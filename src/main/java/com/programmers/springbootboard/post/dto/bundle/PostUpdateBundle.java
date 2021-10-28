package com.programmers.springbootboard.post.dto.bundle;

import com.programmers.springbootboard.member.domain.vo.Email;
import com.programmers.springbootboard.post.domain.vo.Content;
import com.programmers.springbootboard.post.domain.vo.Title;
import lombok.Builder;

@Builder
public class PostUpdateBundle {
    private final Email email;
    private final Long postId;
    private final Title title;
    private final Content content;

    public Email getEmail() {
        return email;
    }

    public Long getPostId() {
        return postId;
    }

    public Title getTitle() {
        return title;
    }

    public Content getContent() {
        return content;
    }
}
