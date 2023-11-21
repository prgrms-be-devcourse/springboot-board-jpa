package com.programmers.springboard.service;

import org.springframework.stereotype.Service;

import com.programmers.springboard.entity.Member;
import com.programmers.springboard.exception.MemberNotFoundException;
import com.programmers.springboard.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	public Member getMemberById(Long id){
		return memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
	}
}
