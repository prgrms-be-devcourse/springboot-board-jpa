package com.programmers.springboard.service;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.programmers.springboard.member.domain.Member;
import com.programmers.springboard.member.exception.DuplicateIdException;
import com.programmers.springboard.member.exception.LoginFailException;
import com.programmers.springboard.member.exception.MemberNotFoundException;
import com.programmers.springboard.member.repository.MemberRepository;
import com.programmers.springboard.member.dto.MemberCreateRequest;
import com.programmers.springboard.member.dto.MemberLoginRequest;
import com.programmers.springboard.member.dto.MemberUpdateRequest;
import com.programmers.springboard.member.dto.MemberLoginResponse;
import com.programmers.springboard.member.dto.MemberResponse;
import com.programmers.springboard.member.service.MemberService;

@SpringBootTest
class MemberServiceTest {

	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	private static Member member;

	@BeforeEach
	void setUp() {
		String encodedPassword = passwordEncoder.encode("test1234");
		Member memberToSave = new Member("testuser","testid",encodedPassword);
		member = memberRepository.save(memberToSave);
	}

	@AfterEach
	void cleanUp() {
		memberRepository.deleteAll();
	}

	@Test
	void 회원가입_성공() {
		// Given
		MemberCreateRequest memberCreateRequest = new MemberCreateRequest("choeunbi","eunbi","test1234");

		// When
		MemberResponse createdMember = memberService.createMember(memberCreateRequest);

		// Then
		assertThat(createdMember.loginId(), is(memberCreateRequest.loginId()));
		assertThat(createdMember.name(), is(memberCreateRequest.name()));
		memberService.deleteMembers(List.of(createdMember.id()));
	}

	@Test
	void 중복된_아이디_회원가입_실패() {
		// Given
		MemberCreateRequest duplicateRequest = new MemberCreateRequest(member.getName(),member.getLoginId(),member.getPassword());

		// When // Then
		assertThrows(DuplicateIdException.class, () -> memberService.createMember(duplicateRequest));
	}

	@Test
	void 로그인_성공() {
		// Given
		MemberLoginRequest memberLoginRequest = new MemberLoginRequest(member.getLoginId(), "test1234");

		// When
		MemberLoginResponse response = memberService.login(memberLoginRequest);

		// Then
		assertThat(response.memberId(), is(member.getId()));
	}

	@Test
	void 잘못된_비밀번호_로그인_실패() {
		// Given
		MemberLoginRequest memberLoginRequest = new MemberLoginRequest(member.getLoginId(), "wrongpassword");

		// When // Then
		assertThrows(LoginFailException.class, () -> memberService.login(memberLoginRequest));
	}

	@Test
	void 회원_조회_성공() {
		// Given

		// When
		MemberResponse foundMember = memberService.getMemberById(member.getId());

		// Then
		assertThat(foundMember.id(), is(member.getId()));
		assertThat(foundMember.name(), is(member.getName()));
		assertThat(foundMember.loginId(), is(member.getLoginId()));
	}

	@Test
	void 존재하지_않는_회원_조회_실패() {
		// Given

		// When // Then
		assertThrows(MemberNotFoundException.class, () -> memberService.getMemberById(member.getId()+1));
	}

	@Test
	void 회원_비밀번호_변경_성공() {
		// Given
		MemberUpdateRequest request = new MemberUpdateRequest("newpassword");

		// When
		MemberResponse updatedMember = memberService.updateMember(member.getId(), request);

		// Then
		assertThat(updatedMember.id(), is(member.getId()));
		assertThat(updatedMember.name(), is(member.getName()));
		assertThat(updatedMember.loginId(), is(member.getLoginId()));
	}

	@Test
	void 회원_삭제_성공() {
		// Given

		// When
		memberService.deleteMembers(List.of(member.getId()));

		// Then
		Optional<Member> deletedMember = memberRepository.findById(member.getId());
		assertThat(deletedMember.isPresent(), is(false));
	}
}
