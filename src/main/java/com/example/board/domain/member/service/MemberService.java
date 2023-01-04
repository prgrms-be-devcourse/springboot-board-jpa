package com.example.board.domain.member.service;

import com.example.board.domain.member.dto.MemberRequest;
import com.example.board.domain.member.dto.MemberResponse;

public interface MemberService {

  MemberResponse.Detail findById(Long id);

  Long save(MemberRequest.SignUp signUpRequest);

  Long login(MemberRequest.Login loginRequest);

}
