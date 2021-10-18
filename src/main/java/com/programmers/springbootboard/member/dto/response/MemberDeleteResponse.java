package com.programmers.springbootboard.member.dto.response;

import com.programmers.springbootboard.annotation.ThreadSafety;
import lombok.Builder;
import lombok.NonNull;

@ThreadSafety
@Builder
public class MemberDeleteResponse {
    @NonNull
    private final Long id;
    @NonNull
    private final String email;

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
