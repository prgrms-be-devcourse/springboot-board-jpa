package com.programmers.springbootboard.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class MemberSignResponse {
    @NonNull
    private final Long memberId;
    @NonNull
    private final String email;
    @NonNull
    private final String name;
    @NonNull
    private final Integer age;
    @NonNull
    private final String hobby;
}
