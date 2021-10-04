package com.programmers.springbootboard.member.converter;

import com.programmers.springbootboard.member.domain.Member;
import com.programmers.springbootboard.member.domain.vo.Age;
import com.programmers.springbootboard.member.domain.vo.Email;
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
                .email(new Email(request.getEmail()))
                .name(new Name(request.getName()))
                .age(new Age(request.getAge()))
                .hobby(new Hobby(request.getHobby()))
                .build();
    }

    public MemberDetailResponse toMemberDetailResponse(Member member) {
        return MemberDetailResponse.builder()
                .email(member.getEmail().getEmail())
                .name(member.getName().getName())
                .age(member.getAge().toString())
                .hobby(member.getHobby().getHobby())
                .build();
    }

    public MemberSignRequest toMemberSignRequest(Email email, Name name, Age age, Hobby hobby) {
        return MemberSignRequest.builder()
                .email(email.getEmail())
                .name(name.getName())
                .age(age.toString())
                .hobby(hobby.getHobby())
                .build();
    }

    public MemberUpdateRequest toMemberUpdateRequest(Email email, Name name, Age age, Hobby hobby) {
        return MemberUpdateRequest.builder()
                .email(email)
                .name(name)
                .age(age)
                .hobby(hobby)
                .build();
    }
}
