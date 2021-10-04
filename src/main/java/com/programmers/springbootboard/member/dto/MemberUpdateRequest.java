package com.programmers.springbootboard.member.dto;

import com.programmers.springbootboard.member.domain.vo.Age;
import com.programmers.springbootboard.member.domain.vo.Email;
import com.programmers.springbootboard.member.domain.vo.Hobby;
import com.programmers.springbootboard.member.domain.vo.Name;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberUpdateRequest {
    private Email email;
    private Name name;
    private Age age;
    private Hobby hobby;
}
