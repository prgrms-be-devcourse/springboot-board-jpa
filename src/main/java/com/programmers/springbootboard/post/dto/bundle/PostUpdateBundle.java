package com.programmers.springbootboard.post.dto.bundle;

import com.programmers.springbootboard.annotation.ThreadSafety;
import com.programmers.springbootboard.member.domain.vo.Email;
import com.programmers.springbootboard.post.domain.vo.Content;
import com.programmers.springbootboard.post.domain.vo.Title;
import lombok.Builder;

@ThreadSafety
@Builder
public class PostUpdateBundle {
    private final Email email;
    private final Long id;
    private final Title title;
    private final Content content;

    public Email getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public Title getTitle() {
        return title;
    }

    public Content getContent() {
        return content;
    }
}
