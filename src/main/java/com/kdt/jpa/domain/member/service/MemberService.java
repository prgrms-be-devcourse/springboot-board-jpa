package com.kdt.jpa.domain.member.service;

import com.kdt.jpa.domain.member.dto.MemberRequest;
import com.kdt.jpa.domain.member.dto.MemberResponse;

public interface MemberService {
	MemberResponse.JoinMemberResponse join(MemberRequest.JoinMemberRequest request);

	MemberResponse findById(Long id);
}
