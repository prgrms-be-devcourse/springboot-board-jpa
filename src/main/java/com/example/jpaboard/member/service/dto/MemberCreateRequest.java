package com.example.jpaboard.member.service.dto;

import com.example.jpaboard.member.domain.Age;
import com.example.jpaboard.member.domain.Name;

public record MemberCreateRequest(Name name,
                                  Age age,
                                  String hobby) { }
