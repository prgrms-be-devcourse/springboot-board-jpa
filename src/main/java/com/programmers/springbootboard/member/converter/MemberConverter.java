package com.programmers.springbootboard.member.converter;

import com.programmers.springbootboard.member.domain.Member;
import com.programmers.springbootboard.member.dto.MemberDetailResponse;
import com.programmers.springbootboard.member.dto.MemberSignRequest;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter {
    public Member of(MemberSignRequest request) {
        return Member.builder()
                .name(request.getName())
                .age(request.getAge())
                .hobby(request.getHobby())
                .build();
    }

    public MemberDetailResponse toMemberDetailResponse(Member member) {
        return MemberDetailResponse.builder()
                .name(member.getName())
                .age(member.getAge())
                .hobby(member.getHobby())
                .build();
    }
}
