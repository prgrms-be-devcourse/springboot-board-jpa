package com.programmers.springboard.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.programmers.springboard.entity.Member;
import com.programmers.springboard.exception.MemberNotFoundException;
import com.programmers.springboard.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	public Member getMemberById(Long id){
		return memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
	}

	@Transactional(readOnly = true)
	public Member getMemberByLoginId(String id){
		return memberRepository.findByLoginId(id).orElseThrow(MemberNotFoundException::new);
	}

	@Transactional(readOnly = true)
	public Member login(String username, String credentials) {
		Member member = memberRepository.findByLoginId(username)
				.orElseThrow(MemberNotFoundException::new);

		member.checkPassword(passwordEncoder, credentials);
		return member;
	}
}
