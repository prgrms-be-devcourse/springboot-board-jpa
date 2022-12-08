package com.kdt.jpa.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.kdt.jpa.domain.member.dto.MemberRequest;
import com.kdt.jpa.domain.member.dto.MemberResponse;
import com.kdt.jpa.exception.ServiceException;

@SpringBootTest
@Transactional
class DefaultMemberServiceTest {

	@Autowired
	MemberService memberService;

	@Test
	@DisplayName("회원 가입 성공 테스트")
	void join() {
		//given
		MemberRequest.JoinMemberRequest request = new MemberRequest.JoinMemberRequest("jinhyungPark", 27, "개발");

		//when
		MemberResponse.JoinMemberResponse joinedMember = memberService.join(request);

		//then
		assertThat(joinedMember.id()).isNotNull();
	}

	@Test
	@DisplayName("존재하는 ID로 회원 조회를 한다.")
	void findByExistId() {
		//given
		MemberRequest.JoinMemberRequest request = new MemberRequest.JoinMemberRequest("jinhyungPark", 27, "개발");
		MemberResponse.JoinMemberResponse joinedMember = memberService.join(request);

		//when
		MemberResponse foundMember = memberService.findById(joinedMember.id());

		//then
		assertThat(foundMember.id()).isEqualTo(joinedMember.id());
		assertThat(foundMember.name()).isEqualTo(request.name());
		assertThat(foundMember.age()).isEqualTo(request.age());
		assertThat(foundMember.hobby()).isEqualTo(request.hobby());
	}

	@Test
	@DisplayName("존재하지 않는 ID로 회원 조회는 실패해야 한다.")
	void findByNotExistId() {
		//given
		Long notExistId = -987654321L;

		//when, then
		assertThatThrownBy(() -> memberService.findById(notExistId)).isInstanceOf(ServiceException.class);
	}

}