package com.programmers.springboard.service;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.springboard.config.jwt.JwtAuthentication;
import com.programmers.springboard.config.jwt.JwtAuthenticationToken;
import com.programmers.springboard.entity.Member;
import com.programmers.springboard.exception.MemberNotFoundException;
import com.programmers.springboard.repository.MemberRepository;
import com.programmers.springboard.request.MemberCreateRequest;
import com.programmers.springboard.request.MemberLoginRequest;
import com.programmers.springboard.request.MemberUpdateRequest;
import com.programmers.springboard.response.MemberLoginResponse;
import com.programmers.springboard.response.MemberResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	public MemberLoginResponse login(MemberLoginRequest request) {
		JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(request.loginId(), request.password());
		Authentication authenticated = authenticationManager.authenticate(authenticationToken);
		JwtAuthentication authentication = (JwtAuthentication) authenticated.getPrincipal();
		Member member = (Member) authenticated.getDetails();

		// 마지막 로그인 날짜를 현재 시간으로 업데이트
		member.updateLastLoginDate();
		memberRepository.save(member);
		return new MemberLoginResponse(authentication.getToken() , member.getId(), member.getRole().name());
	}

	@Transactional(readOnly = true)
	public MemberResponse getMemberById(Long id) {
		return memberRepository.findById(id)
			.map(MemberResponse::of)
			.orElseThrow(MemberNotFoundException::new);
	}

	public MemberResponse createMember(MemberCreateRequest request) {
		String encodedPassword = passwordEncoder.encode(request.password());
		Member member = request.toEntity(encodedPassword);
		Member savedMember = memberRepository.save(member);
		return MemberResponse.of(savedMember);
	}

	public MemberResponse updateMember(Long id, MemberUpdateRequest request) {
		Member member = memberRepository.findById(id)
			.orElseThrow(MemberNotFoundException::new);
		String encodedPassword = passwordEncoder.encode(request.password());
		member.changePassword(encodedPassword);
		return MemberResponse.of(member);
	}

	public void deleteMembers(List<Long> ids) {
		List<Member> members = memberRepository.findAllById(ids);
		memberRepository.deleteAll(members);
	}

}
