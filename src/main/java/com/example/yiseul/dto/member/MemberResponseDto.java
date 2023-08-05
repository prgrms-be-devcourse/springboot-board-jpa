package com.example.yiseul.dto.member;

import java.time.LocalDateTime;

public record MemberResponseDto(Long id, String name, int age, String hobby, String createdAt, String createdBy) {

}
