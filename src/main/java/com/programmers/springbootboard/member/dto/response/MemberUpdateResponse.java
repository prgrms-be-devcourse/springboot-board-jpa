package com.programmers.springbootboard.member.dto.response;

import com.programmers.springbootboard.annotation.ThreadSafety;
import lombok.Builder;
import lombok.NonNull;

@ThreadSafety
@Builder
public class MemberUpdateResponse {
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

    public Long getMemberId() {
        return memberId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getHobby() {
        return hobby;
    }
}
