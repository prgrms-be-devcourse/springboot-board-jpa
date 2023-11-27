package com.programmers.springboard.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.springboard.entity.Member;
import com.programmers.springboard.exception.MemberNotFoundException;
import com.programmers.springboard.repository.MemberRepository;
import com.programmers.springboard.request.MemberCreateRequest;
import com.programmers.springboard.request.MemberUpdateRequest;
import com.programmers.springboard.response.MemberResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService {

	private final MemberRepository memberRepository;

	@Transactional(readOnly = true)
	public List<MemberResponse> getMembers() {
		List<Member> members = memberRepository.findAll();
		return members.stream().map(MemberResponse::of).toList();
	}

	@Transactional(readOnly = true)
	public MemberResponse getMemberById(Long id) {
		return memberRepository.findById(id)
			.map(MemberResponse::of)
			.orElseThrow(MemberNotFoundException::new);
	}

	public MemberResponse createMember(MemberCreateRequest request) {
		Member member = memberRepository.save(Member.builder()
			.name(request.name())
			.age(request.age())
			.hobby(request.hobby())
			.build());
		return MemberResponse.of(member);
	}

	public MemberResponse updateMember(Long id, MemberUpdateRequest request) {
		Member member = memberRepository.findById(id)
			.orElseThrow(MemberNotFoundException::new);
		member.updateMemberNameAndHobby(request.name(), request.hobby());
		return MemberResponse.of(member);
	}

	public void deleteMember(Long id) {
		Member member = memberRepository.findById(id)
			.orElseThrow(MemberNotFoundException::new);
		memberRepository.delete(member);
	}
}
