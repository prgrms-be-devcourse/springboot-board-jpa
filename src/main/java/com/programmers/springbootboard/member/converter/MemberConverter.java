package com.programmers.springbootboard.member.converter;

import com.programmers.springbootboard.member.domain.Member;
import com.programmers.springbootboard.member.domain.vo.*;
import com.programmers.springbootboard.member.dto.response.MemberSignResponse;
import com.programmers.springbootboard.member.dto.response.MemberUpdateResponse;
import com.programmers.springbootboard.member.dto.bundle.MemberDeleteBundle;
import com.programmers.springbootboard.member.dto.bundle.MemberFindBundle;
import com.programmers.springbootboard.member.dto.bundle.MemberSignBundle;
import com.programmers.springbootboard.member.dto.request.MemberSignRequest;
import com.programmers.springbootboard.member.dto.request.MemberUpdateRequest;
import com.programmers.springbootboard.member.dto.response.MemberDeleteResponse;
import com.programmers.springbootboard.member.dto.response.MemberDetailResponse;
import com.programmers.springbootboard.member.dto.bundle.MemberUpdateBundle;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter {
    public MemberSignBundle toMemberSignBundle(MemberSignRequest request) {
        return MemberSignBundle.builder()
                .email(new Email(request.getEmail()))
                .name(new Name(request.getName()))
                .age(new Age(request.getAge()))
                .hobby(new Hobby(request.getHobby()))
                .build();
    }

    public Member toMember(MemberSignBundle bundle) {
        return Member.builder()
                .email(bundle.getEmail())
                .name(bundle.getName())
                .age(bundle.getAge())
                .hobby(bundle.getHobby())
                .build();
    }

    public MemberSignResponse toMemberSignResponse(Member member) {
        return MemberSignResponse.builder()
                .memberId(member.getId())
                .email(member.getEmail().getEmail())
                .name(member.getName().getName())
                .age(member.getAge().getAge())
                .hobby(member.getHobby().getHobby())
                .build();
    }

    public MemberDeleteBundle toMemberDeleteBundle(Long id) {
        return MemberDeleteBundle.builder()
                .memberId(id)
                .build();
    }

    public MemberFindBundle toMemberFindBundle(Long id) {
        return MemberFindBundle.builder()
                .id(id)
                .build();
    }

    public MemberDeleteResponse toMemberDeleteResponse(Long id, Email email) {
        return MemberDeleteResponse.builder()
                .memberId(id)
                .email(email.getEmail())
                .build();
    }

    public MemberUpdateBundle toMemberUpdateBundle(Long id, MemberUpdateRequest request) {
        return MemberUpdateBundle.builder()
                .id(id)
                .name(new Name(request.getName()))
                .age(new Age(request.getAge()))
                .hobby(new Hobby(request.getHobby()))
                .build();
    }

    public MemberUpdateResponse toMemberUpdateResponse(Member member) {
        return MemberUpdateResponse.builder()
                .memberId(member.getId())
                .email(member.getEmail().getEmail())
                .name(member.getName().getName())
                .age(member.getAge().getAge())
                .hobby(member.getHobby().getHobby())
                .build();
    }

    public MemberDetailResponse toMemberDetailResponse(Member member) {
        return MemberDetailResponse.builder()
                .memberId(member.getId())
                .email(member.getEmail().getEmail())
                .name(member.getName().getName())
                .age(member.getAge().toString())
                .hobby(member.getHobby().getHobby())
                .build();
    }
}
