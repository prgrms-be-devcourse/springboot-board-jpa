package com.example.board.domain.member.service;

import com.example.board.domain.common.exception.NotFoundException;
import com.example.board.domain.member.dto.MemberRequest;
import com.example.board.domain.member.dto.MemberResponse;
import com.example.board.domain.member.entity.Member;
import com.example.board.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {

  private final MemberRepository memberRepository;

  public MemberService(MemberRepository memberRepository){
    this.memberRepository = memberRepository;
  }

  @Transactional(readOnly = true)
  public MemberResponse.Detail findById(Long id) {
    Member member = memberRepository.findById(id)
        .orElseThrow(
            () -> new NotFoundException(String.format("NotFoundException member id: %d", id)));
    return MemberResponse.Detail.from(member);
  }

  public Long save(MemberRequest memberRequest) {
    Member member = memberRequest.toEntity();
    Member saved = memberRepository.save(member);
    return saved.getId();
  }
}
