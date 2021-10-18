package com.programmers.springbootboard.member.dto.bundle;

import com.programmers.springbootboard.annotation.ThreadSafety;
import lombok.Builder;

@ThreadSafety
@Builder
public class MemberDeleteBundle {
    private final Long memberId;

    public Long getMemberId() {
        return memberId;
    }
}
