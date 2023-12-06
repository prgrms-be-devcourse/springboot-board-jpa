package com.example.board.global.security.guard.member;

import com.example.board.domain.member.entity.Member;
import com.example.board.domain.member.repository.MemberRepository;
import com.example.board.global.exception.CustomException;
import com.example.board.global.exception.ErrorCode;
import com.example.board.global.security.guard.Guard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.example.board.global.security.util.SecurityUtil.getCurrentUserId;

@Component
@RequiredArgsConstructor
public class MemberGuard extends Guard {

    private final MemberRepository memberRepository;

    @Override
    protected boolean isResourceOwner(Long id) {
        Member member = memberRepository.findById(id)
            .orElseThrow(() -> new CustomException(ErrorCode.ACCESS_DENIED));

        Long memberId = getCurrentUserId()
            .orElseThrow(() -> new CustomException(ErrorCode.ACCESS_DENIED));

        return member.getId().equals(memberId);
    }
}
