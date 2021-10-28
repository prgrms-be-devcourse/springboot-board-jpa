package com.programmers.springbootboard.member.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberUpdateRequest {
    private String name;
    private String age;
    private String hobby;
}
