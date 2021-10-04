package com.programmers.springbootboard.member.dto;

import com.programmers.springbootboard.member.domain.vo.Email;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberSignRequest {
    private String email;
    private String name;
    private String age;
    private String hobby;
}
