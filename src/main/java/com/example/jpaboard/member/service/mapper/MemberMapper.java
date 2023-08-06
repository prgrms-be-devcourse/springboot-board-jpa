package com.example.jpaboard.member.service.mapper;

import com.example.jpaboard.member.domain.Age;
import com.example.jpaboard.member.domain.Member;
import com.example.jpaboard.member.service.dto.CreateMemberRequest;

import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    private MemberMapper() { }

    public Member to(CreateMemberRequest createMemberRequest) {
        String name = createMemberRequest.name();
        Age age = createMemberRequest.age();
        String hobby = createMemberRequest.hobby();

        return new Member(name, age, hobby);
    }

}
