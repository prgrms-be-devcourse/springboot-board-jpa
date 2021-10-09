package com.programmers.springbootboard.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberDeleteResponse {
    private Long id;
    private String email;
}
