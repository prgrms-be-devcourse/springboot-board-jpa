package com.spring.board.springboard.user.service;

import com.spring.board.springboard.user.domain.Member;
import com.spring.board.springboard.user.domain.dto.MemberDetailResponseDto;
import com.spring.board.springboard.user.domain.dto.MemberLoginDto;
import com.spring.board.springboard.user.domain.dto.MemberRequestDto;
import com.spring.board.springboard.user.exception.AuthenticateException;
import com.spring.board.springboard.user.exception.NoMemberException;
import com.spring.board.springboard.user.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class MemberService {


    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> {
                    throw new NoMemberException("회원 정보를 찾을 수 없습니다.");
                });
    }

    public MemberDetailResponseDto findById(Integer memberId) {
        final Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    throw new NoMemberException("회원 정보를 찾을 수 없습니다.");
                });

        return new MemberDetailResponseDto(findMember);
    }

    @Transactional
    public void register(MemberRequestDto memberRequestDto) {
        checkDuplicateEmail(memberRequestDto.email());
        final Member newMember = memberRequestDto.toEntity();
        memberRepository.save(newMember);
    }

    public MemberDetailResponseDto login(MemberLoginDto memberLoginDto) {
        final Member findMember = memberRepository.findByEmail(
                        memberLoginDto.email())
                .orElseThrow(() -> {
                    throw new AuthenticateException("회원 정보가 잘못되었습니다.");
                });

        findMember.login(
                memberLoginDto.password());

        return new MemberDetailResponseDto(findMember);
    }

    public MemberDetailResponseDto findByEmail(String email) {
        final Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> {
                    throw new NoMemberException("회원 정보를 찾을 수 없습니다.");
                });

        return new MemberDetailResponseDto(findMember);
    }

    private void checkDuplicateEmail(String requestEmail){
        if(memberRepository.existsByEmail(requestEmail)){
            throw new AuthenticateException("이미 가입된 이메일입니다.");
        }
    }
}
