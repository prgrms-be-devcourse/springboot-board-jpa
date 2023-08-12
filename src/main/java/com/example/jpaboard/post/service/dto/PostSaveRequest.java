package com.example.jpaboard.post.service.dto;

public record PostSaveRequest(Long memberId,
                              String title,
                              String content) { }
