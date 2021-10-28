package com.programmers.springbootboard.member.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberSignRequest {
    private String email;
    private String name;
    private String age;
    private String hobby;
}
