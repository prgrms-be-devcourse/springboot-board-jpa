package com.example.board.domain.member.service.v1;

import com.example.board.common.exception.NotFoundException;
import com.example.board.common.exception.UnAuthorizedException;
import com.example.board.domain.member.dto.MemberRequest;
import com.example.board.domain.member.dto.MemberResponse;
import com.example.board.domain.member.entity.Member;
import com.example.board.domain.member.repository.MemberRepository;
import com.example.board.domain.member.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CookieMemberService implements MemberService {

  private final MemberRepository memberRepository;

  public CookieMemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public MemberResponse.Detail findById(Long id) {
    Member member = memberRepository.findById(id)
        .orElseThrow(
            () -> new NotFoundException(String.format("member id %d doesn't exist", id)));
    return new MemberResponse.Detail(member);
  }

  @Override
  public Long save(MemberRequest.SignUp signUpRequest) {
    Member member = signUpRequest.toEntity();
    memberRepository.save(member);
    return member.getId();
  }

  @Override
  @Transactional(readOnly = true)
  public Long login(MemberRequest.Login loginRequest) {
    Member member = memberRepository.findByEmail(loginRequest.email())
        .orElseThrow(
            () -> new UnAuthorizedException(
                String.format("email %s not found", loginRequest.email())));

    try{
      member.login(loginRequest.password());
      return member.getId();

    } catch(IllegalArgumentException e){
      throw new UnAuthorizedException(
          String.format("password doesn't match. request: %s, actual: %s", loginRequest.password(), member.getPassword()));
    }
  }
}
