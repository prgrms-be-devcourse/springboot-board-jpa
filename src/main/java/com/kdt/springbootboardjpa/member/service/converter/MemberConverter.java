package com.kdt.springbootboardjpa.member.service.converter;

import com.kdt.springbootboardjpa.member.domain.Member;
import com.kdt.springbootboardjpa.member.service.dto.MemberRequest;
import com.kdt.springbootboardjpa.member.service.dto.MemberResponse;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter {

    public Member requestToMember(MemberRequest request) {
        return Member.builder()
                .name(request.getName())
                .age(request.getAge())
                .hobby(request.getHobby())
                .build();
    }

    public MemberResponse memberToResponse(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .age(member.getAge())
                .hobby(member.getHobby())
                .build();
    }
}
