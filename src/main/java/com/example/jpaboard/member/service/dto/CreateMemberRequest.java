package com.example.jpaboard.member.service.dto;

import com.example.jpaboard.member.domain.Age;

public record CreateMemberRequest(String name, Age age, String hobby) {

}
