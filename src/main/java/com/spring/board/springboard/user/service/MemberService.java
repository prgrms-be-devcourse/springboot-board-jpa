package com.spring.board.springboard.user.service;

import com.spring.board.springboard.user.domain.Member;
import com.spring.board.springboard.user.exception.NoMemberException;
import com.spring.board.springboard.user.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member find(Integer memberId){
        return memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    throw new NoMemberException(
                            MessageFormat.format("No Member data by id : {0}", memberId));
                });
    }
}
