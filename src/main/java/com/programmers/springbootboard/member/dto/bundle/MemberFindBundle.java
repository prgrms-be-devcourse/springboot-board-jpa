package com.programmers.springbootboard.member.dto.bundle;

import com.programmers.springbootboard.annotation.ThreadSafety;
import lombok.Builder;

@ThreadSafety
@Builder
public class MemberFindBundle {
    private final Long id;

    public Long getId() {
        return id;
    }
}
