package com.example.jpaboard.member.service.mapper;

import com.example.jpaboard.member.domain.Age;
import com.example.jpaboard.member.domain.Member;
import com.example.jpaboard.member.domain.Name;
import com.example.jpaboard.member.service.dto.MemberCreateRequest;

import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    private MemberMapper() { }

    public Member to(MemberCreateRequest memberCreateRequest) {
        Name name = memberCreateRequest.name();
        Age age = memberCreateRequest.age();
        String hobby = memberCreateRequest.hobby();

        return new Member(name, age, hobby);
    }

}
