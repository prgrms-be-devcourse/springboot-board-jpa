package com.kdt.jpa.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kdt.jpa.domain.member.MemberConverter;
import com.kdt.jpa.domain.member.dto.MemberRequest;
import com.kdt.jpa.domain.member.dto.MemberResponse;
import com.kdt.jpa.domain.member.model.Member;
import com.kdt.jpa.domain.member.repository.MemberRepository;
import com.kdt.jpa.exception.ServiceException;

@ExtendWith(MockitoExtension.class)
public class DefaultMemberServiceUnitTest {

	@InjectMocks
	DefaultMemberService memberService;

	@Mock
	MemberRepository memberRepository;

	@Spy
	MemberConverter converter = new MemberConverter();

	@Test
	@DisplayName("회원 가입 성공 테스트")
	void join() {
		//given
		MemberRequest.JoinMemberRequest request = new MemberRequest.JoinMemberRequest("jinhyungPark", 27, "개발");
		Member newMember = new Member(1L, "jinhyungPark", 27, "개발");
		given(memberRepository.save(any(Member.class))).willReturn(newMember);

		//when
		MemberResponse.JoinMemberResponse joinedMember = memberService.join(request);

		//then
		verify(converter, times(1)).toEntity(request);
		verify(memberRepository, times(1)).save(any(Member.class));
		verify(converter, times(1)).toJoinMemberResponse(newMember);

		assertThat(joinedMember.id()).isNotNull();
	}

	@Test
	@DisplayName("존재하는 ID로 회원 조회를 한다.")
	void findByExistId() {
		//given
		Member member = new Member(1L, "jinhyungPark", 27, "개발");
		given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));

		//when
		MemberResponse foundMember = memberService.findById(member.getId());

		//then
		verify(memberRepository, times(1)).findById(member.getId());
		verify(converter, times(1)).toMemberResponse(member);

		assertThat(foundMember.id()).isEqualTo(member.getId());
		assertThat(foundMember.name()).isEqualTo(member.getName());
		assertThat(foundMember.age()).isEqualTo(member.getAge());
		assertThat(foundMember.hobby()).isEqualTo(member.getHobby());
	}

	@Test
	@DisplayName("존재하지 않는 ID로 회원 조회는 실패해야 한다.")
	void findByNotExistId() {
		//given
		Long notExistId = -987654321L;
		given(memberRepository.findById(notExistId)).willReturn(Optional.empty());

		//when, then
		assertThatThrownBy(() -> memberService.findById(notExistId)).isInstanceOf(ServiceException.class);

		verify(memberRepository, times(1)).findById(notExistId);
	}

}
