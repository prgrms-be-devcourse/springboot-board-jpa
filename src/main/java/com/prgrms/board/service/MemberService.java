package com.prgrms.board.service;

import com.prgrms.board.dto.MemberCreateDto;
import com.prgrms.board.dto.MemberResponseDto;
import org.springframework.transaction.annotation.Transactional;

public interface MemberService {
    @Transactional
    Long join(MemberCreateDto createDto);

    MemberResponseDto findById(Long memberId);
}
