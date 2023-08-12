package com.example.jpaboard.post.service.dto;

public record PostUpdateRequest(String title,
                                String content,
                                Long memberId) { }
