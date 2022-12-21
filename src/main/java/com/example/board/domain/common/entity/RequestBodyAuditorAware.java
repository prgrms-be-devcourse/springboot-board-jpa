package com.example.board.domain.common.entity;

import com.example.board.domain.member.dto.MemberResponse.Detail;
import com.example.board.domain.member.service.MemberService;
import com.example.board.domain.post.dto.PostRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class RequestBodyAuditorAware implements AuditorAware<String> {

  private final MemberService memberService;

  public RequestBodyAuditorAware(MemberService memberService){
    this.memberService = memberService;
  }

  @Override
  public Optional<String> getCurrentAuditor() {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

    StringBuilder stringBuilder = new StringBuilder();
    BufferedReader br;

    String line;
    try {
      InputStream inputStream = request.getInputStream();
      br = new BufferedReader(new InputStreamReader(inputStream));
      while ((line = br.readLine()) != null) {
        stringBuilder.append(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    ObjectMapper mapper = new ObjectMapper();
    try {
      PostRequest postRequest = mapper.readValue(stringBuilder.toString(), PostRequest.class);
      Detail foundMember = memberService.findById(
          postRequest.getMemberId());
      return Optional.of(foundMember.getName());
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
