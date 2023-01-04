package com.spring.board.springboard.user.service;

import com.spring.board.springboard.user.domain.Member;
import com.spring.board.springboard.user.domain.dto.MemberDetailResponseDto;
import com.spring.board.springboard.user.domain.dto.MemberLoginDto;
import com.spring.board.springboard.user.domain.dto.MemberRequestDto;
import com.spring.board.springboard.user.exception.NoMemberException;
import com.spring.board.springboard.user.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;

@Transactional
@Service
public class MemberService {


    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public Member getMember(Integer memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    throw new NoMemberException("사용자 정보를 찾을 수 없습니다.");
                });
    }

    @Transactional(readOnly = true)
    public MemberDetailResponseDto findById(Integer memberId) {
        final Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    throw new NoMemberException("사용자 정보를 찾을 수 없습니다.");
                });

        return new MemberDetailResponseDto(findMember);
    }

    public void register(MemberRequestDto memberRequestDto) {
        final Member newMember = memberRequestDto.toEntity();
        memberRepository.save(newMember);
    }

    @Transactional(readOnly = true)
    public MemberDetailResponseDto login(MemberLoginDto memberLoginDto) {
        final Member findMember = memberRepository.findByEmail(
                        memberLoginDto.email())
                .orElseThrow(() -> {
                    throw new NoMemberException(
                            MessageFormat.format("입력한 email: [{0}] 정보로 회원을 찾을 수 없습니다.", memberLoginDto.email()));
                });

        findMember.login(
                memberLoginDto.password());

        return new MemberDetailResponseDto(findMember);
    }

    public MemberDetailResponseDto findByEmail(String email) {
        final Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> {
                    throw new NoMemberException("사용자 정보를 찾을 수 없습니다.");
                });

        return new MemberDetailResponseDto(findMember);
    }
}
