package com.kdt.jpa.domain.member.service;

import java.text.MessageFormat;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kdt.jpa.domain.member.MemberConverter;
import com.kdt.jpa.domain.member.dto.MemberRequest;
import com.kdt.jpa.domain.member.dto.MemberResponse;
import com.kdt.jpa.domain.member.model.Member;
import com.kdt.jpa.domain.member.repository.MemberRepository;
import com.kdt.jpa.exception.ErrorCode;
import com.kdt.jpa.exception.ServiceException;

@Service
@Transactional
public class DefaultMemberService implements MemberService {

	private final MemberRepository memberRepository;
	private final MemberConverter memberConverter;

	public DefaultMemberService(MemberRepository memberRepository, MemberConverter memberConverter) {
		this.memberRepository = memberRepository;
		this.memberConverter = memberConverter;
	}

	@Override
	public MemberResponse.JoinMemberResponse join(MemberRequest.JoinMemberRequest request) {
		Member newMember = this.memberConverter.toEntity(request);

		return this.memberConverter.toJoinMemberResponse(
			this.memberRepository.save(newMember)
		);
	}

	@Override
	@Transactional(readOnly = true)
	public MemberResponse findById(Long id) {
		return memberConverter.toMemberResponse(
			this.memberRepository.findById(id)
				.orElseThrow(() -> new ServiceException
					(
						MessageFormat.format("{0} - error target : {1}", ErrorCode.MEMBER_NOT_FOUND.getMessage(), id),
						ErrorCode.MEMBER_NOT_FOUND
					)
				)
		);
	}
}
