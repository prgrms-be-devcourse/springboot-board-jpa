package com.example.jpaboard.member.service.dto;

import com.example.jpaboard.member.domain.Member;
import java.util.List;
import java.util.stream.Collectors;

public record FindMemberResponses(List<FindMemberResponse> findMemberResponses) {

    public FindMemberResponses of(List<Member> members) {
        List<FindMemberResponse> responses = members.stream().map(FindMemberResponse::new)
                .collect(Collectors.toList());
        return new FindMemberResponses(responses);
    }

}
