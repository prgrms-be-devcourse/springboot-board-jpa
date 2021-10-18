package com.programmers.springbootboard.member.dto.response;

import com.programmers.springbootboard.annotation.ThreadSafety;
import lombok.Builder;
import lombok.NonNull;

@ThreadSafety
@Builder
public class MemberDeleteResponse {
    @NonNull
    private final Long memberId;
    @NonNull
    private final String email;

    public Long getMemberId() {
        return memberId;
    }

    public String getEmail() {
        return email;
    }
}
