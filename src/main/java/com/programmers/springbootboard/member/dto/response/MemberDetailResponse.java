package com.programmers.springbootboard.member.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class MemberDetailResponse {
    @NonNull
    private Long memberId;
    @NonNull
    private String email;
    @NonNull
    private String name;
    @NonNull
    private String age;
    @NonNull
    private String hobby;

    public Long getMemberId() {
        return memberId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getHobby() {
        return hobby;
    }
}
