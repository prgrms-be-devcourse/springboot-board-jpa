package com.seungwon.board.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seungwon.board.member.application.dto.MemberRequestDto;
import com.seungwon.board.member.domain.Member;
import com.seungwon.board.member.infra.MemberRepository;

@Service
@Transactional
public class MemberService {
	private final MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public Long create(MemberRequestDto memberRequestDto) {
		Member member = Member.builder()
				.age(26)
				.hobby("운동")
				.name("한승원")
				.build();
		Member result = memberRepository.save(member);
		return result.getId();
	}

}
