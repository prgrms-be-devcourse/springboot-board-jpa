package com.programmers.springbootboard.member.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

// lombok을 쓰지 않을거면, 아에 쓰지 말자!!
@Getter
@Builder
public class MemberDetailResponse {
    @NonNull
    private final Long memberId;
    @NonNull
    private final String email;
    @NonNull
    private final String name;
    @NonNull
    private final String age;
    @NonNull
    private final String hobby;
}
