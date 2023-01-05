package com.spring.board.springboard.user.domain.dto;

import com.spring.board.springboard.user.domain.Member;

public record MemberSummaryResponseDto(String email, String name) {

    public MemberSummaryResponseDto(Member member) {
        this(
                member.getEmail(),
                member.getName()
        );
    }
}
