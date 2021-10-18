package com.programmers.springbootboard.member.dto.bundle;

import lombok.Builder;

@Builder
public class MemberDeleteBundle {
    private Long id;

    public Long getId() {
        return id;
    }
}
