package com.programmers.springbootboard.member.dto.bundle;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberDeleteBundle {
    private final Long memberId;
}
