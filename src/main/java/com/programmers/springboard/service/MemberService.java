package com.programmers.springboard.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	public MemberLoginResponse login(MemberLoginRequest request) {
		// JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(request.loginId(), request.password());
		// Authentication authenticated = authenticationManager.authenticate(authenticationToken);
		// JwtAuthentication authentication = (JwtAuthentication)authenticated.getPrincipal();
		// Member member = (Member)authenticated.getDetails();
		return new MemberLoginResponse("token", 1L, List.of("group"));
	}

	@Transactional(readOnly = true)
	public MemberResponse getMemberById(Long id) {
		return memberRepository.findById(id)
			.map(MemberResponse::of)
			.orElseThrow(MemberNotFoundException::new);
	}

	public MemberResponse createMember(MemberCreateRequest request) {
		Member savedMember = memberRepository.save(request.toEntity());
		return MemberResponse.of(savedMember);
	}

	public MemberResponse updateMember(Long id, MemberUpdateRequest request) {
		Member member = memberRepository.findById(id)
			.orElseThrow(MemberNotFoundException::new);
		member.changePassword(request.password());
		return MemberResponse.of(member);
	}

	public void deleteMembers(List<Long> ids) {
		List<Member> members = memberRepository.findAllById(ids);  // todo : 만약 없는 회원이라면?
		memberRepository.deleteAll(members);
	}

}
