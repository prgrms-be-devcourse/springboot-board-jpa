package com.programmers.springbootboard.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberDetailResponse {
    private String email;
    private String name;
    private String age;
    private String hobby;
}
