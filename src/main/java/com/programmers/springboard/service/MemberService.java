package com.programmers.springboard.service;

import com.programmers.springboard.entity.Groups;
import com.programmers.springboard.exception.GroupNotFoundException;
import com.programmers.springboard.repository.GroupRepository;
import com.programmers.springboard.request.SignupRequest;
import com.programmers.springboard.response.MemberResponse;
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
	private final GroupRepository groupRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	public Member getMemberById(Long id){
		return memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
	}

	@Transactional(readOnly = true)
	public Member getMemberByLoginId(String id){
		return memberRepository.findByLoginId(id).orElseThrow(MemberNotFoundException::new);
	}

	public Member login(String username, String credentials) {
		Member member = memberRepository.findByLoginId(username)
				.orElseThrow(MemberNotFoundException::new);

		member.checkPassword(passwordEncoder, credentials);
		member.changeLoginAt();
		return member;
	}

	public Member signup(SignupRequest request) {
		Groups group = getGroup(request.GroupId());
		String encodedPw = passwordEncoder.encode(request.passwd());
		Member member = Member.builder()
				.name(request.name())
				.age(request.age())
				.hobby(request.hobby())
				.loginId(request.loginId())
				.passwd(encodedPw)
				.groups(group)
				.build();

		return memberRepository.save(member);
	}

	public Groups getGroup(Long id) {
		return groupRepository.findById(id).orElseThrow(GroupNotFoundException::new);
	}

    public void editMemberGroup(Long memberId, Long groupId) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(MemberNotFoundException::new);
		Groups group = getGroup(groupId);

		member.changeGroup(group);
	}
}
