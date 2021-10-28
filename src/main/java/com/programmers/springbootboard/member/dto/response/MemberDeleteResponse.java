package com.programmers.springbootboard.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class MemberDeleteResponse {
    @NonNull
    private final Long memberId;
    @NonNull
    private final String email;
}
