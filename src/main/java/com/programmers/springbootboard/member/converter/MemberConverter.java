package com.programmers.springbootboard.member.converter;

import com.programmers.springbootboard.member.domain.Member;
import com.programmers.springbootboard.member.domain.vo.Age;
import com.programmers.springbootboard.member.domain.vo.Hobby;
import com.programmers.springbootboard.member.domain.vo.Name;
import com.programmers.springbootboard.member.dto.MemberDetailResponse;
import com.programmers.springbootboard.member.dto.MemberSignRequest;
import com.programmers.springbootboard.member.dto.MemberUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter {
    public Member toMember(MemberSignRequest request) {
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

    public MemberSignRequest toMemberSignRequest(Name name, Age age, Hobby hobby) {
        return MemberSignRequest.builder()
                .name(name)
                .age(age)
                .hobby(hobby)
                .build();
    }

    public MemberUpdateRequest toMemberUpdateRequest(Name name, Age age, Hobby hobby) {
        return MemberUpdateRequest.builder()
                .name(name)
                .age(age)
                .hobby(hobby)
                .build();
    }
}
