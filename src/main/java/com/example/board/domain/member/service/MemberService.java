package com.example.board.domain.member.service;

import com.example.board.common.exception.NotFoundException;
import com.example.board.common.exception.UnAuthorizedException;
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

  public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  @Transactional(readOnly = true)
  public MemberResponse.Detail findById(Long id) {
    Member member = memberRepository.findById(id)
        .orElseThrow(
            () -> new NotFoundException(String.format("member id %d doesn't exist", id)));
    return new MemberResponse.Detail(member);
  }

  public Long save(MemberRequest.SignUp signUpRequest) {
    Member member = signUpRequest.toEntity();
    memberRepository.save(member);
    return member.getId();
  }

  public Long login(MemberRequest.Login loginRequest) {
    Member member = memberRepository.findByEmail(loginRequest.email())
        .orElseThrow(
            () -> new UnAuthorizedException(
                String.format("email %s not found", loginRequest.email())));

    if(wrongPassword(loginRequest.password(), member.getPassword())){
      throw new UnAuthorizedException(
          String.format("password doesn't match. request: %s, actual: %s", loginRequest.password(), member.getPassword()));
    }

    return member.getId();
  }

  private boolean wrongPassword(String expected, String actual){
    return !expected.equals(actual);
  }
}
