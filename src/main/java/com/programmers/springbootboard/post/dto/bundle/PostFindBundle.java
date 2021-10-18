package com.programmers.springbootboard.post.dto.bundle;

import com.programmers.springbootboard.annotation.ThreadSafety;
import com.programmers.springbootboard.member.domain.vo.Email;
import lombok.Builder;

@ThreadSafety
@Builder
public class PostFindBundle {
    private final Long id;

    public Long getId() {
        return id;
    }
}
